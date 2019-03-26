package com.se.rxjavademo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author : garyhu
 * Since : 2019/3/22
 */

public class HookClickUtil {

    public void hookClick(View view) {
        try {
            //通过完整的类名拿到class
            Class<?> viewClass = Class.forName("android.view.View");
            Method getListenerInfo = viewClass.getDeclaredMethod("getListenerInfo");
            getListenerInfo.setAccessible(true);
            Object listenerInfo = getListenerInfo.invoke(view);
            // 得到 原始的 OnClickListener 对象
            Class<?> listenerInfoClz = Class.forName("android.view.View$ListenerInfo");
            Field mOnClickListener = listenerInfoClz.getDeclaredField("mOnClickListener");
            mOnClickListener.setAccessible(true);
            View.OnClickListener originOnClickListener = (View.OnClickListener) mOnClickListener.get(listenerInfo);


            ClickInvocationHandler handler = new ClickInvocationHandler(originOnClickListener);
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    originOnClickListener.getClass().getInterfaces(), handler);
            mOnClickListener.set(listenerInfo, proxy);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private class ClickInvocationHandler implements InvocationHandler {

        private Object iActivityManagerObject;

        private ClickInvocationHandler(Object iActivityManagerObject) {
            this.iActivityManagerObject = iActivityManagerObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            // 在原来的方法前后加上我们自己的处理
            Log.e("ClickInvocationHandler","点击事件之前的小处理");
            Object result = method.invoke(iActivityManagerObject, args);
            Log.e("ClickInvocationHandler","点击事件已经处理完毕！！");
            return result;
        }
    }
}
