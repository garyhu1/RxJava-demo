package com.se.mvplibrary.presenter;

import android.util.Log;

import com.se.mvplibrary.base.BasePresenter;
import com.se.mvplibrary.contract.MainContract;


/**MainPresenter
 * Created by Administrator on 2018/5/7.
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter{


    @Override
    public void testGetMpresenter() {
        Log.d("print", "我是P层的引用");
        mView.testGetMview();
    }

    @Override
    public void testDb() {
        mDataManager.testDb();
    }

    @Override
    public void testRequestNetwork() {
        mDataManager.testRequestNetwork();
    }

    @Override
    public void testPreference() {
        mDataManager.testPreference();
    }
}
