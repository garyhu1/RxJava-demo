package com.se.businessone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.se.bottomlibrary.base.activity.BaseActivity;
import com.se.bottomlibrary.base.utils.ConstantRouter;

/**
 * Author : garyhu
 * Since : 2019/3/26.
 */

@Route(path = ConstantRouter.SHOW_DETAIL)
public class ShowDetailActivity extends BaseActivity {

    @Autowired
    String name;

    @Autowired
    int age;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_detail);

        TextView nameTxt = findViewById(R.id.name);
        TextView ageTxt = findViewById(R.id.age);

        nameTxt.setText("姓名 ： " + name);
        ageTxt.setText("年龄 ： " + age);

    }
}
