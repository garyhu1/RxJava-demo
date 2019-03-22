package com.se.rxjavademo;

import android.app.Application;

import com.se.rxjavademo.activity.MainActivity;
import com.se.rxjavademo.utils.HookUtil;

/**
 * Created by yb on 2019/3/22.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        HookUtil hookUtil=new HookUtil(MainActivity.class, this);
//        hookUtil.hookAms();
    }
}
