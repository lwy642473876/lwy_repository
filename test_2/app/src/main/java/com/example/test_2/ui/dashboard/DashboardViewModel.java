package com.example.test_2.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class DashboardViewModel extends ViewModel {



    private MutableLiveData<String> mText;

    public DashboardViewModel() {


        mText = new MutableLiveData<>();
        mText.setValue("这里是我的信息");
    }

    public LiveData<String> getText() {
        return mText;
    }

}