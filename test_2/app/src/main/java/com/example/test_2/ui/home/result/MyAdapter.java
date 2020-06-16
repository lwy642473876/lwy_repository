package com.example.test_2.ui.home.result;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.test_2.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<Data> mData;
    private Context mContext;

    public MyAdapter(List<Data> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.pro_mini_image = (ImageView) convertView.findViewById(R.id.pro_mini_image);
            viewHolder.pro_price = (TextView) convertView.findViewById(R.id.pro_price);
            viewHolder.pro_short = (TextView) convertView.findViewById(R.id.pro_short);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        String date = Data.getDate();
        //String date = "2020-05-12";
        Bitmap bitmap = BitmapFactory.decodeStream(getClass().getResourceAsStream("/res/drawable/smiley_3.webp"));
        try {
            bitmap = GetBitmap.getBitmap(mData.get(position).getImg());
        } catch (IOException e) {
            e.printStackTrace();
        }

        viewHolder.pro_mini_image.setImageBitmap(bitmap);
        viewHolder.pro_short.setText(mData.get(position).getType());
        viewHolder.pro_price.setText("￥" + mData.get(position).getLowest_price(date) +
                "~" + "￥" +  mData.get(position).getHighest_price(date));

        return convertView;
    }

    private class ViewHolder{
        ImageView pro_mini_image;
        TextView pro_short;
        TextView pro_price;
    }



}
