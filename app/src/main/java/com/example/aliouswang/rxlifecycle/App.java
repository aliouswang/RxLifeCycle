package com.example.aliouswang.rxlifecycle;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by aliouswang on 2018/8/13.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        LeakCanary.install(this);
    }
}
