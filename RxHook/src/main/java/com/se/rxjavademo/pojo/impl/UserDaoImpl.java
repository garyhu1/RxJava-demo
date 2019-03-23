package com.se.rxjavademo.pojo.impl;

import android.util.Log;

import com.se.rxjavademo.pojo.UserDao;

/**
 * Created by yb on 2019/3/22.
 */

public class UserDaoImpl implements UserDao{

    @Override
    public void add() {
        Log.e("garyhu","增");
    }

    @Override
    public void update() {
        Log.e("garyhu","修改");
    }

    @Override
    public void insert() {
        Log.e("garyhu","插入");
    }

    @Override
    public void delete() {
        Log.e("garyhu","删");
    }
}
