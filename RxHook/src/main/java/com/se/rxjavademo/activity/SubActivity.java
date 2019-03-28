package com.se.rxjavademo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.se.bottomlibrary.base.activity.BaseActivity;
import com.se.rxjavademo.R;

/**
 * Author    : garyhu
 * Since     : 2019/3/28
 * Describe  : 该Activity只是用来占坑，使AMS校验通过
 */

public class SubActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sub);
    }
}
