package com.se.rxjavademo.hook;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.se.rxjavademo.utils.FieldUtils;

/**
 * Author    : garyhu
 * Since     : 2019/3/28
 * Describe  :
 */

public class HCallback implements Handler.Callback {

    public static final int LAUNCH_ACTIVITY = 100;
    Handler mHandler;
    public HCallback(Handler handler) {
        mHandler = handler;
    }


    @Override
    public boolean handleMessage(Message msg) {
        Log.e("garyhu","msg");
        if (msg.what == LAUNCH_ACTIVITY) {
            Object r = msg.obj;
            try {
                //得到消息中的Intent(启动SubActivity的Intent)
                Intent intent = (Intent) FieldUtils.getField(r.getClass(), r, "intent");
                Log.e("garyhu","取出Intent = "+intent.getComponent());
                //得到此前保存起来的Intent(启动TargetActivity的Intent)
                Intent target = intent.getParcelableExtra(HookHelper.TARGET_INTENT);
                if(target != null){
                    Log.e("garyhu","target = "+target.getComponent());
                    //将启动SubActivity的Intent替换为启动TargetActivity的Intent
                    intent.setComponent(target.getComponent());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mHandler.handleMessage(msg);
        return true;

    }
}
