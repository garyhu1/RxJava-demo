package com.se.rxjavademo;

import android.content.Context;

import com.se.bottomlibrary.base.BaseApplication;
import com.se.rxjavademo.hook.HookHelper;

/**
 * Author    : garyhu
 * Since     : 2019/3/28
 * Describe  :
 */

public class MyApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        try {
            // IActivityManager实现插件化（暂时没有成功）
//            HookHelper.hookAMS();
//            HookHelper.hookHandler();
            // Instrumentation方案实现插件化
            HookHelper.hookInstrumentation(base);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
