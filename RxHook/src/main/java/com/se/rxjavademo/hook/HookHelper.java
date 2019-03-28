package com.se.rxjavademo.hook;

import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Context;
import android.os.Build;
import android.os.Handler;

import com.se.rxjavademo.utils.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * Author    : garyhu
 * Since     : 2019/3/28
 * Describe  :
 */

public class HookHelper {

    public static final String TARGET_INTENT = "TargetActivity";
    public static final String TARGET_INTENT_NAME = "TargetActivity2";

    public static void hookAMS() throws Exception {

        Object defaultSingleton = null;
        if (Build.VERSION.SDK_INT >= 26) {//1
            Class<?> activityManageClazz = Class.forName("android.app.ActivityManager");
            //获取activityManager中的IActivityManagerSingleton字段
            defaultSingleton = FieldUtils.getField(activityManageClazz, null, "IActivityManagerSingleton");
        } else {
            Class<?> activityManagerNativeClazz = Class.forName("android.app.ActivityManagerNative");
            //获取ActivityManagerNative中的gDefault字段
            defaultSingleton = FieldUtils.getField(activityManagerNativeClazz, null, "gDefault");
        }

        Class<?> singletonClazz = Class.forName("android.util.Singleton");
        Field mInstanceField= FieldUtils.getField(singletonClazz ,"mInstance");//2
        //获取iActivityManager
        Object iActivityManager = mInstanceField.get(defaultSingleton);//3
//        Class<?> iActivityManagerClazz = Class.forName("android.app.IActivityManager");
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                iActivityManager.getClass().getInterfaces(), new IActivityManagerProxy(iActivityManager));
        mInstanceField.set(defaultSingleton, proxy);

    }

    public static void hookHandler() throws Exception {
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Object currentActivityThread= FieldUtils.getField(activityThreadClass ,null,"sCurrentActivityThread");//1
        Field mHField = FieldUtils.getField(activityThreadClass,"mH");//2
        Handler mH = (Handler) mHField.get(currentActivityThread);//3
        FieldUtils.setField(Handler.class,mH,"mCallback",new HCallback(mH));
    }

    public static void hookInstrumentation(Context context) throws Exception {
        Class<?> contextImplClass = Class.forName("android.app.ContextImpl");
        Field mMainThreadField  =FieldUtils.getField(contextImplClass,"mMainThread");//1
        Object activityThread = mMainThreadField.get(context);//2
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Field mInstrumentationField=FieldUtils.getField(activityThreadClass,"mInstrumentation");//3
        FieldUtils.setField(activityThreadClass,activityThread,"mInstrumentation",new InstrumentationProxy((Instrumentation) mInstrumentationField.get(activityThread),
                context.getPackageManager()));
    }



}
