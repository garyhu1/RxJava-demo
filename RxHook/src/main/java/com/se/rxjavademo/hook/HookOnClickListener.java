package com.se.rxjavademo.hook;

import android.util.Log;
import android.view.View;

/**
 * Author : garyhu
 * Since : 2019/3/21
 */

public class HookOnClickListener implements View.OnClickListener {

    private final static String TAG = HookOnClickListener.class.getSimpleName();

    private View.OnClickListener origin;

    public HookOnClickListener(View.OnClickListener origin){
        this.origin = origin;
    }

    @Override
    public void onClick(View v) {
//        Toast.makeText(MainActivity.this, "hook click", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"Before click, do what you want to to.");
        if (origin != null) {
            origin.onClick(v);
        }
        Log.d(TAG,"After click, do what you want to to.");
    }
}
