package com.example.test_2.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.test_2.R;
import com.example.test_2.ui.home.http.GetJsonDataUtil;
import com.example.test_2.ui.home.http.GetURLData;
import com.example.test_2.ui.home.http.HttpUtil;
import com.example.test_2.ui.home.result.Data;
import com.example.test_2.ui.home.result.NewListFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Response;

public class SearchResult extends FragmentActivity {
    private TextView txt_title;
    private FrameLayout fl_content;
    private Context mContext;
    private ArrayList<Data> datas = null;
    private FragmentManager fManager = null;
    private long exitTime = 0;
    private String input;
    private String url_server;
    private int pro_num;
    //@BindView(R.id.topbar_result)
    //QMUITopBar mTopBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_result);
        QMUIStatusBarHelper.translucent(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //View root = LayoutInflater.from(this).inflate(R.layout.activity_result, null);
        //ButterKnife.bind(this, root);
        //initTopBar();
        setContentView(R.layout.activity_result);

        mContext = SearchResult.this;

        fManager = getSupportFragmentManager();
        bindViews();

        Intent intent = getIntent();
        String input = intent.getStringExtra("input");

        if (isHttpUrl(input)) {
            pro_num = 1;
            url_server = "http://192.168.175.1:6555/get_serving?url_or_keyword=" + input;
        }
        else {
            pro_num = 8;
            url_server = "http://192.168.175.1:6555/get_serving?url_or_keyword=" + input + "&item_num=" + pro_num;
        }
        String JosnData = new String();

        //sendRequestWithOkHttp();
        //Type listType = new TypeToken<ArrayList<Data>>(){}.getType();
        //datas = new Gson().fromJson(JosnData, listType);
        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        try {
            JosnData = GetURLData.getURLData(url_server);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type listType = new TypeToken<ArrayList<Data>>(){}.getType();
        datas = new Gson().fromJson(JosnData, listType);

        if (datas != null){
            NewListFragment nlFragment = new NewListFragment(fManager, datas);
            FragmentTransaction ft = fManager.beginTransaction();
            ft.replace(R.id.fl_content, nlFragment);
            ft.commit();
        }
        else{
            Toast.makeText(SearchResult.this, "什么都没有找到, 请返回", Toast.LENGTH_SHORT).show();
        }
    }


        private void bindViews() {
            fl_content = (FrameLayout) findViewById(R.id.fl_content);

        }

    private static boolean isHttpUrl(String urls) {
        boolean isurl = false;
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式

        Pattern pat = Pattern.compile(regex.trim());//对比
        Matcher mat = pat.matcher(urls.trim());
        isurl = mat.matches();//判断是否匹配
        if (isurl) {
            isurl = true;
        }
        return isurl;
    }



    /*private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //在子线程中执行Http请求，并将最终的请求结果回调到okhttp3.Callback中
                HttpUtil.sendOkHttpRequest(url_server,new okhttp3.Callback(){
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //得到服务器返回的具体内容
                        String responseData=response.body().string();
                        Type listType = new TypeToken<ArrayList<Data>>(){}.getType();
                        datas = new Gson().fromJson(responseData, listType);
                    }
                    @Override
                    public void onFailure(Call call,IOException e){
                        //在这里进行异常情况处理
                    }
                });
            }
        }).start();

    }*/

}
