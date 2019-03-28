package com.se.rxjavademo.hook;

import android.content.Intent;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Author    : garyhu
 * Since     : 2019/3/28
 * Describe  :
 */

public class IActivityManagerProxy implements InvocationHandler {

    private Object mActivityManager;
    private static final String TAG = "IActivityManagerProxy";

    public IActivityManagerProxy(Object origin){
        this.mActivityManager = origin;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if("startActivity".equals(method.getName())){
            Intent intent = null;
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                if(args[i] instanceof Intent){
                    index = i;
                    break;
                }
            }

            intent = (Intent) args[index];

            Intent subIntent = new Intent();
            String packageName = "com.se.rxjavademo.activity";
            subIntent.setClassName(packageName,packageName+".SubActivity");
            subIntent.putExtra(HookHelper.TARGET_INTENT,intent);

            args[index] = subIntent;

            Log.e("garyhu","代理了 = "+intent.getComponent());
        }
        return method.invoke(mActivityManager,args);
    }
}
