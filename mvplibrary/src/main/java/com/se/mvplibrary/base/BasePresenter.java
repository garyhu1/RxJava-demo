package com.se.mvplibrary.base;


import com.se.mvplibrary.model.DataManager;
import com.se.mvplibrary.model.db.AppDbHelper;
import com.se.mvplibrary.model.db.DbHelper;
import com.se.mvplibrary.model.http.ApiHelper;
import com.se.mvplibrary.model.http.AppApiHelper;
import com.se.mvplibrary.model.preference.AppPreferenceHelper;
import com.se.mvplibrary.model.preference.PreferenceHelper;

/**BasePresenter
 * Created by Administrator on 2018/5/7.
 */

public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    protected DataManager mDataManager;
    protected V mView;

     public BasePresenter(){
         AppDbHelper appDbHelper = new DbHelper();
         AppPreferenceHelper appPreferenceHelper = new PreferenceHelper();
         AppApiHelper appApiHelper = new ApiHelper();
         mDataManager = new DataManager(appDbHelper, appApiHelper, appPreferenceHelper);
    }

    @Override
    public void attachView(V view) {
        this.mView = view;
    }
}
