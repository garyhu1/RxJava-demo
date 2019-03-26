package com.se.bottomlibrary.base.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.se.bottomlibrary.R;
import com.se.bottomlibrary.base.activity.BaseActivity;

/**
 * Created by yb on 2019/3/25.
 */

@Route(path = "/base/login")
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
