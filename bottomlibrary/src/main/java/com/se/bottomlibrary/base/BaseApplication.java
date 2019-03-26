package com.se.bottomlibrary.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.se.bottomlibrary.BuildConfig;

/**
 * Created by yb on 2019/3/25.
 */

public class BaseApplication extends Application {

    private BaseApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
        INSTANCE = this;
    }

    public BaseApplication getINSTANCE(){
        return INSTANCE;
    }
}
