package com.se.businessone;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.se.bottomlibrary.base.activity.BaseActivity;

/**
 * Created by yb on 2019/3/26.
 */

@Route(path = "/show/detail")
public class ShowDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_detail);
    }
}
