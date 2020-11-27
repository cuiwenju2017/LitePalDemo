package com.example.litepaldemo;

import android.app.Application;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}
