package com.example.test_2.ui.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.test_2.MainActivity;
import com.example.test_2.R;
import com.example.test_2.ui.home.http.GetJsonDataUtil;
import com.example.test_2.ui.home.result.Data;
import com.example.test_2.ui.home.result.NewListFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private String userName, psw, spPsw;
    private Button button_login;
    private TextView tv_usr;
    private QMUIRoundButton qrb_coll;
    private QMUIRoundButton qrb_quit;
    private EditText et_user;
    private EditText et_psw;
    private TextView tv_register;
    private boolean flag_login;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", getActivity().MODE_PRIVATE);
        flag_login = sp.getBoolean("isLogin", false);
        if (!flag_login){
            init();
        }
        else{
            afterLogin();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", getActivity().MODE_PRIVATE);
        flag_login = sp.getBoolean("isLogin", false);
        View root;
        if (!flag_login){
            root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        }
        else{
            root = inflater.inflate(R.layout.fragment_afterlogin, container, false);
        }
        //final TextView textView = root.findViewById(R.id.text_dashboard);
        /*dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        return root;
    }

    private void init(){
        tv_register = (TextView) getActivity().findViewById(R.id.tv_register);
        button_login = (Button) getActivity().findViewById(R.id.btn_login);
        et_user = (EditText) getActivity().findViewById(R.id.et_user_name);
        et_psw = (EditText) getActivity().findViewById(R.id.et_psw);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //为了跳转到注册界面，并实现注册功能
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始登录，获取用户名和密码 getText().toString().trim();
                userName = et_user.getText().toString().trim();
                psw = et_psw.getText().toString().trim();
                //对当前用户输入的密码进行MD5加密再进行比对判断, MD5Utils.md5( ); psw 进行加密判断是否一致
                String md5Psw = MD5Utils.md5(psw);
                // md5Psw ; spPsw 为 根据从SharedPreferences中用户名读取密码
                // 定义方法 readPsw为了读取用户名，得到密码
                spPsw = readPsw(userName);
                // TextUtils.isEmpty
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(getActivity(), "请输入用户名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(getActivity(), "请输入密码", Toast.LENGTH_SHORT).show();
                    // md5Psw.equals(); 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
                } else if (md5Psw.equals(spPsw)) {
                    //一致登录成功
                    Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
                    //保存登录状态，在界面保存登录的用户名 定义个方法 saveLoginStatus boolean 状态 , userName 用户名;
                    saveLoginStatus(true, userName);
                    //MainActivity.flag_login = true;
                    //登录成功后关闭此页面进入主页
                    Intent data = new Intent();
                    //datad.putExtra( ); name , value ;
                    data.putExtra("isLogin", true);
                    //RESULT_OK为Activity系统常量，状态码为-1
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    getActivity().setResult(getActivity().RESULT_OK, data);
                    //销毁登录界面
                    //getActivity().finish();
                    //跳转到主界面，登录成功的状态传递到 MainActivity 中
                    startActivity(new Intent(getActivity(), MainActivity.class));

                } else if ((spPsw != null && !TextUtils.isEmpty(spPsw) && !md5Psw.equals(spPsw))) {
                    Toast.makeText(getActivity(), "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "此用户名不存在", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     *从SharedPreferences中根据用户名读取密码
     */
    private String readPsw(String userName){
        //getSharedPreferences("loginInfo",MODE_PRIVATE);
        //"loginInfo",mode_private; MODE_PRIVATE表示可以继续写入
        SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", getActivity().MODE_PRIVATE);
        //sp.getString() userName, "";
        return sp.getString(userName , "");
    }
    /**
     *保存登录状态和登录用户名到SharedPreferences中
     */
    private void saveLoginStatus(boolean status,String userName){
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", getActivity().MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName);
        //提交修改
        editor.apply();
    }

    private void afterLogin(){
        tv_usr = (TextView) getActivity().findViewById(R.id.tv_usr);
        qrb_coll = (QMUIRoundButton) getActivity().findViewById(R.id.qrb_coll);
        qrb_quit = (QMUIRoundButton) getActivity().findViewById(R.id.qrb_quit);
        SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", getActivity().MODE_PRIVATE);
        userName = sp.getString("loginUserName", "");
        tv_usr.setText("用户" + userName + "已登录");
        qrb_coll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CollectActivity.class);
                startActivity(intent);
            }
        });
        qrb_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isLogin", false);
                editor.apply();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "账号已退出", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 注册成功的数据返回至此
     * @param requestCode 请求码
     * @param resultCode 结果码
     * @param data 数据
     */
    @Override
    //显示数据， onActivityResult
    //startActivityForResult(intent, 1); 从注册界面中获取数据
    //int requestCode , int resultCode , Intent data
    // LoginActivity -> startActivityForResult -> onActivityResult();
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //是获取注册界面回传过来的用户名
            // getExtra().getString("***");
            String userName=data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                //设置用户名到 et_user_name 控件
                et_user.setText(userName);
                //et_user_name控件的setSelection()方法来设置光标位置
                et_user.setSelection(userName.length());
            }
        }
    }


}
