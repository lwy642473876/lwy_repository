package com.example.test_2.ui.home.result;

import android.icu.util.Calendar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Data {
    private String brand = "";
    private String coupons = "";
    private Map<String, String[]> date_price;
    private String img = "";
    private String name = "";
    private String shop = "";
    private String source = "";
    private String type = "";
    private String url = "";
    private boolean isCollect = false;
    private String rank = "";

    public Data(){}

    public Data(String brand, String coupons, Map<String, String[]> date_price, String rank,
                String img, String name, String shop, String source, String type, String url, boolean isCollect) {
        this.url = url;
        this.shop = shop;
        this.name = name;
        this.type = type;
        this.img = img;
        this.coupons = coupons;
        this.source = source;
        this.date_price = date_price;
        this.brand = brand;
        this.isCollect = isCollect;
        this.rank = rank;
    }

    public String getUrl(){
        return url;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getShop() {
        return shop;
    }

    public String getSource() {
        return source;
    }

    public String getImg() {
        return img;
    }

    public String getCoupons() {
        return coupons;
    }

    public Map<String, String[]> getDate_price() {
        return date_price;
    }

    public String getBrand() {
        return brand;
    }

    public boolean getIsCollect(){
        return isCollect;
    }

    public String getRank() {
        return rank;
    }

    public static String getDate() {
        Calendar calendar=Calendar.getInstance();
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH) + 1;
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String Year = String.format("%04d", year);
        String Month = String.format("%02d", month);
        String Day = String.format("%02d", day);
        return Year + "-" + Month + "-03";
    }

    public String getHighest_price(String date) {
        return getDate_price().get(date)[1];
    }

    public String getLowest_price(String date) {
        return getDate_price().get(date)[0];
    }

    public String getPlatform(){
        if (getSource().equals("jd")) {
            return "京东";
        }
        else if (getSource().equals("sn")) {
            return "苏宁";
        }
        else if (getSource().equals("tb")) {
            return "淘宝";
        }
        else if (getSource().equals("tm")) {
            return "天猫";
        }
        else {
            return "nothing";
        }
    }

    public ArrayList<String> getAllDate(){
        ArrayList<String> all_date = new ArrayList<>();
        for(String key : getDate_price().keySet()){
            all_date.add(key);
        }
        return all_date;
    }

    public ArrayList<String> getAllLowPrice() {
        ArrayList<String> all_low_price = new ArrayList<>();
        for(String key : getDate_price().keySet()){
            all_low_price.add(getDate_price().get(key)[0]);
        }
        return all_low_price;
    }

    public void setDate_price(Map<String, String[]> date_price) {
        this.date_price = date_price;
    }

    public void setCoupons(String coupons) {
        this.coupons = coupons;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}

