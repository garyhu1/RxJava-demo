package com.se.rxjavademo.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Author : garyhu
 * Since : 2019/3/22
 */

public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Log.d("garyhu",method.getName() + " ---> 开始处理问题");
        Object result = method.invoke(target, args);
        Log.d("garyhu",method.getName() + " ---> 问题处理完毕");

        return result;
    }
}
