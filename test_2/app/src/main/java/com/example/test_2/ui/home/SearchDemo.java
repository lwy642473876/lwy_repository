package com.example.test_2.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_2.R;

import com.example.test_2.ui.home.db.DbDao;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchDemo extends AppCompatActivity {

    //@BindView(R.id.topbar_search)
    //QMUITopBar mTopBar1;

    private QMUIRoundButton mbtn_serarch;
    private EditText met_search;
    private RecyclerView mRecyclerView;
    private TextView mtv_deleteAll;
    private  SeachRecordAdapter mAdapter;

    private DbDao mDbDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //View root = LayoutInflater.from(this).inflate(R.layout.activity_search, null);
        //ButterKnife.bind(this, root);
        //initTopBar();
        setContentView(R.layout.activity_search);
        //setContentView(R.layout.activity_search);
        initViews();
    }

    private void initViews() {
        mDbDao =new DbDao(this);
        mbtn_serarch = (QMUIRoundButton) findViewById(R.id.btn_serarch);
        met_search = (EditText) findViewById(R.id.et_search);
        mtv_deleteAll = (TextView) findViewById(R.id.tv_deleteAll);
        mtv_deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbDao.deleteData();
                mAdapter.updata(mDbDao.queryData(""));
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter =new SeachRecordAdapter(mDbDao.queryData(""),this);
        mAdapter.setRvItemOnclickListener(new BaseRecycleAdapter.RvItemOnclickListener() {
            @Override
            public void RvItemOnclick(int position) {
                mDbDao.delete(mDbDao.queryData("").get(position));

                mAdapter.updata(mDbDao.queryData(""));
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        //事件监听
        mbtn_serarch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (met_search.getText().toString().trim().length() != 0){
                    boolean hasData = mDbDao.hasData(met_search.getText().toString().trim());
                    if (!hasData){
                        mDbDao.insertData(met_search.getText().toString().trim());
                    }else {
                        Toast.makeText(SearchDemo.this, "该内容已在历史记录中", Toast.LENGTH_SHORT).show();
                    }
                    Bundle bd = new Bundle();
                    bd.putString("search_content", met_search.getText().toString());
                    //
                    mAdapter.updata(mDbDao.queryData(""));
                    Intent intent = new Intent(SearchDemo.this, SearchResult.class);
                    intent.putExtra("input", met_search.getText().toString());
                    startActivity(intent);

                }else {
                    Toast.makeText(SearchDemo.this, "请输入内容", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /*private void initTopBar() {

        //mTopBar.setBackgroundColor(ContextCompat.getColor(this, ));
        mTopBar1.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
            }
        });


        mTopBar1.setTitle("比价软件");
    }*/





}
