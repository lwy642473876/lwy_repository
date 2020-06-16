package com.example.test_2.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.test_2.R;
import com.example.test_2.ui.home.SearchResult;
import com.example.test_2.ui.home.http.GetJsonDataUtil;
import com.example.test_2.ui.home.result.Data;
import com.example.test_2.ui.home.result.NewListFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CollectActivity extends FragmentActivity {

    private ArrayList<Data> cDatas = null;
    private FragmentManager fManager = null;
    private FrameLayout fl_collect;
    private Context mContext;
    private String collect_json;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_result);
        QMUIStatusBarHelper.translucent(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //View root = LayoutInflater.from(this).inflate(R.layout.activity_result, null);
        //ButterKnife.bind(this, root);
        //initTopBar();
        setContentView(R.layout.activity_result);

        mContext = CollectActivity.this;

        fManager = getSupportFragmentManager();
        bindViews();

        collect_json = GetJsonDataUtil.getJson("test.json", CollectActivity.this);
        Type listType = new TypeToken<ArrayList<Data>>(){}.getType();
        cDatas = new Gson().fromJson(collect_json, listType);

        NewListFragment nlFragment = new NewListFragment(fManager, cDatas);
        FragmentTransaction ft = fManager.beginTransaction();
        ft.replace(R.id.fl_content, nlFragment);
        ft.commit();
    }

    private void bindViews() {
        fl_collect = (FrameLayout) findViewById(R.id.fl_content);

    }
}
