package com.se.rxjavademo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.se.bottomlibrary.base.activity.BaseActivity;
import com.se.rxjavademo.R;

/**
 * Author    : garyhu
 * Since     : 2019/3/28
 * Describe  : 定义一个没有在Manifest注册的activity,测试插件化Activity,一般该界面是在另一个apk中
 */

public class TargetActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("garyhu","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("garyhu","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("garyhu","onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("garyhu","onDestroy");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.d("garyhu","onBackPressed");
    }
}
