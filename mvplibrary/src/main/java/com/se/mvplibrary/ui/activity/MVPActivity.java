package com.se.mvplibrary.ui.activity;

import android.util.Log;

import com.se.mvplibrary.R;
import com.se.mvplibrary.base.BaseActivity;
import com.se.mvplibrary.contract.MainContract;
import com.se.mvplibrary.presenter.MainPresenter;


public class MVPActivity extends BaseActivity<MainPresenter> implements MainContract.View {


    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter();
    }

    @Override
    protected void initView() {
        mPresenter.testGetMpresenter();
        mPresenter.testDb();
        mPresenter.testRequestNetwork();
        mPresenter.testPreference();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    public void testGetMview() {
        Log.d("print", "我是V层的引用");
    }
}
