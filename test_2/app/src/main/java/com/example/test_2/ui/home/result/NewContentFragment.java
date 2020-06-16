package com.example.test_2.ui.home.result;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.test_2.R;
import com.example.test_2.ui.home.SearchDemo;
import com.github.mikephil.charting.data.LineData;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.io.IOException;

public class NewContentFragment extends Fragment {

    NewContentFragment() {
    }

    private ViewHolder viewHolder;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        viewHolder = new ViewHolder();
        viewHolder.pro_url = (QMUIRoundButton) getActivity().findViewById(R.id.pro_url);
        viewHolder.collect = (QMUIRoundButton) getActivity().findViewById(R.id.collect);
        final boolean isCollect = getArguments().getBoolean("isCollect");
        viewHolder.pro_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getArguments().getString("url"));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        if (!isCollect) {
            viewHolder.collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.collect.setClickable(false);
                    Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                    viewHolder.collect.setText("已收藏");
                    viewHolder.collect.setTextColor(Color.rgb(0, 255, 255));
                }
            });
        }
        else {
            viewHolder.collect.setText("已收藏");
            viewHolder.collect.setTextColor(Color.rgb(0, 255, 255));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content, container, false);
        viewHolder = new ViewHolder();
        viewHolder.pro_image = (ImageView) view.findViewById(R.id.pro_image);
        viewHolder.brand = (TextView) view.findViewById(R.id.brand);
        viewHolder.date = (TextView) view.findViewById(R.id.date);
        viewHolder.platform = (TextView) view.findViewById(R.id.platform);
        viewHolder.pro_name = (TextView) view.findViewById(R.id.pro_name);
        viewHolder.highest_price = (TextView) view.findViewById(R.id.highest_price);
        viewHolder.lowest_price = (TextView) view.findViewById(R.id.lowest_price);
        viewHolder.pro_coupons = (TextView) view.findViewById(R.id.pro_coupons);
        viewHolder.shop = (TextView) view.findViewById(R.id.shop);
        //getArgument获取传递过来的Bundle对象
        Bitmap bitmap = BitmapFactory.decodeStream(getClass().getResourceAsStream("/res/drawable/smiley_3.webp"));
        try {
            bitmap = GetBitmap.getBitmap(getArguments().getString("image"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.pro_image.setImageBitmap(bitmap);
        viewHolder.brand.setText(getArguments().getString("brand"));
        viewHolder.date.setText(getArguments().getString("date"));
        viewHolder.platform.setText(getArguments().getString("platform"));
        viewHolder.pro_name.setText(getArguments().getString("name"));
        viewHolder.highest_price.setText(getArguments().getString("h_price"));
        viewHolder.lowest_price.setText(getArguments().getString("l_price"));
        viewHolder.pro_coupons.setText(getArguments().getString("coupons_url"));
        viewHolder.shop.setText(getArguments().getString("shop"));

        return view;
    }

    private class ViewHolder{
        ImageView pro_image;
        TextView pro_name;
        TextView platform;
        TextView date;
        TextView brand;
        TextView highest_price;
        TextView lowest_price;
        TextView shop;
        TextView pro_coupons;
        QMUIRoundButton pro_url;
        QMUIRoundButton collect;
        LineData mLineData;
    }

}
