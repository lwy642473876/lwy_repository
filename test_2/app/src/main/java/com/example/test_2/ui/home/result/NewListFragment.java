package com.example.test_2.ui.home.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.test_2.R;

import java.util.ArrayList;

public class NewListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private FragmentManager fManager;
    private ArrayList<Data> datas;
    private ListView list_news;

    public NewListFragment(FragmentManager fManager, ArrayList<Data> datas) {
        this.fManager = fManager;
        this.datas = datas;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_newlist, container, false);
        list_news = (ListView) view.findViewById(R.id.list_news);
        MyAdapter myAdapter = new MyAdapter(datas, getActivity());
        list_news.setAdapter(myAdapter);
        list_news.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        NewContentFragment ncFragment = new NewContentFragment();
        Bundle bd = new Bundle();
        bd.putString("image", datas.get(position).getImg());
        bd.putString("name", datas.get(position).getName());
        bd.putString("platform", datas.get(position).getPlatform());
        String date = Data.getDate();
        //String date = "2020-05-12";
        bd.putString("date", date);
        bd.putString("h_price", "最高:￥" + datas.get(position).getHighest_price(date));
        bd.putString("l_price", "最低:￥" + datas.get(position).getLowest_price(date));
        bd.putString("url", datas.get(position).getUrl());
        bd.putString("shop", "店铺：" + datas.get(position).getShop());
        bd.putString("coupons_url", "优惠：" + datas.get(position).getCoupons());
        bd.putString("brand", datas.get(position).getBrand());
        bd.putBoolean("isCollect", datas.get(position).getIsCollect());
        bd.putStringArrayList("all_date", datas.get(position).getAllDate());
        bd.putStringArrayList("all_low_price", datas.get(position).getAllLowPrice());
        datas.get(position).setCollect(true);
        ncFragment.setArguments(bd);
        //加上Fragment替换动画
        fTransaction.setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit);
        fTransaction.replace(R.id.fl_content, ncFragment);
        //调用addToBackStack将Fragment添加到栈中
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }
}

