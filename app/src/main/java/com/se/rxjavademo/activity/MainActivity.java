package com.se.rxjavademo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.se.rxjavademo.R;
import com.se.rxjavademo.api.Api;
import com.se.rxjavademo.hook.HookOnClickListener;
import com.se.rxjavademo.pojo.LocationBean;
import com.se.rxjavademo.pojo.TestA;
import com.se.rxjavademo.pojo.UserDao;
import com.se.rxjavademo.pojo.impl.UserDaoImpl;
import com.se.rxjavademo.proxy.MyInvocationHandler;
import com.se.rxjavademo.utils.HookClickUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private TextView status;
    private Button hookBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        status = findViewById(R.id.status);
        hookBtn = findViewById(R.id.test_hook);

        findViewById(R.id.load).setOnClickListener(v -> {
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                    emitter.onNext("先打个招呼");
                    emitter.onNext("这是正文");
                    emitter.onComplete();
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            Log.d("garyhu","main thread == "+s);
                        }
                    })
                    .observeOn(Schedulers.newThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            Log.e("garyhu","show == "+s);
                        }
                    });
        });

        // map
        findViewById(R.id.request).setOnClickListener(v -> {
            Observable.create(new ObservableOnSubscribe<Response<LocationBean>>() {
                @Override
                public void subscribe(ObservableEmitter<Response<LocationBean>> emitter) throws Exception {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://riab.luokuang.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Api api = retrofit.create(Api.class);
                    Call<LocationBean> call = api.getLocationIcon();
                    
                    call.enqueue(new Callback<LocationBean>() {
                        @Override
                        public void onResponse(Call<LocationBean> call, Response<LocationBean> response) {
                            emitter.onNext(response);
                        }

                        @Override
                        public void onFailure(Call<LocationBean> call, Throwable t) {
                            emitter.onError(t);
                        }
                    });
                }
            })
                    .map(new Function<Response<LocationBean>, LocationBean>() {
                        @Override
                        public LocationBean apply(Response<LocationBean> response) throws Exception {
                            
                            if(response != null && response.isSuccessful()){
                                LocationBean locationBean = response.body();
                                
                                return locationBean;
                            }
                            return null;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Consumer<LocationBean>() {
                        @Override
                        public void accept(LocationBean locationBean) throws Exception {
                            Log.d("garyhu","缓存数据的操作");
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<LocationBean>() {
                        @Override
                        public void accept(LocationBean locationBean) throws Exception {
                            Log.d("garyhu","数据请求成功");
                            String msg = locationBean.getMsg();
                            status.setText(msg);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.e("garyhu","请求失败 : "+ throwable.getLocalizedMessage());
                        }
                    });
        });

        // concat 先本地再网络，onComplete()执行后就执行下个Observable
        Observable locateCache = Observable.create(new ObservableOnSubscribe<LocationBean>() {
            @Override
            public void subscribe(ObservableEmitter<LocationBean> emitter) throws Exception {
                Log.e("garyhu","先从本地获取数据");
                Thread.sleep(3000);
                LocationBean bean = new LocationBean();
                bean.setMsg("从本地缓存取的数据");
                emitter.onNext(bean);
//                emitter.onComplete();
            }
        });

        Observable net = Observable.create(new ObservableOnSubscribe<Response<LocationBean>>() {
            @Override
            public void subscribe(ObservableEmitter<Response<LocationBean>> emitter) throws Exception {
                Log.e("garyhu"," 从网络获取数据 ");
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://riab.luokuang.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);
                Call<LocationBean> call = api.getLocationIcon();

                call.enqueue(new Callback<LocationBean>() {
                    @Override
                    public void onResponse(Call<LocationBean> call, Response<LocationBean> response) {
                        emitter.onNext(response);
                    }

                    @Override
                    public void onFailure(Call<LocationBean> call, Throwable t) {
                        emitter.onError(t);
                    }
                });
            }
        }).map(new Function<Response<LocationBean>, LocationBean>() {
            @Override
            public LocationBean apply(Response<LocationBean> response) throws Exception {
                if(response != null && response.isSuccessful()){
                    LocationBean locationBean = response.body();

                    return locationBean;
                }
                return null;
            }
        });

        findViewById(R.id.getCache).setOnClickListener(v -> {
            Observable.concat(locateCache,net)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<LocationBean>() {
                        @Override
                        public void accept(LocationBean bean) throws Exception {
                            Log.e("garyhu"," msg :: "+ bean.getMsg());
                        }
                    });
        });

        findViewById(R.id.test_static).setOnClickListener(v -> {
            new TestA();
        });

        findViewById(R.id.test_proxy).setOnClickListener(v -> {

            UserDao dao = new UserDaoImpl();

            MyInvocationHandler handler = new MyInvocationHandler(dao);

            UserDao userDao = (UserDao) Proxy.newProxyInstance(dao.getClass().getClassLoader(),
                    dao.getClass().getInterfaces(),handler);

            userDao.add();
            userDao.update();
            userDao.insert();
            userDao.delete();
        });

        hookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity","执行点击事件");
            }
        });

        HookClickUtil hookClickUtil = new HookClickUtil();
        hookClickUtil.hookClick(hookBtn);
    }

    private void hookOnClickListener(View view) {
        try {
            // 得到 View 的 ListenerInfo 对象
            Method getListenerInfo = View.class.getDeclaredMethod("getListenerInfo");
            getListenerInfo.setAccessible(true);
            Object listenerInfo = getListenerInfo.invoke(view);
            // 得到 原始的 OnClickListener 对象
            Class<?> listenerInfoClz = Class.forName("android.view.View$ListenerInfo");
            Field mOnClickListener = listenerInfoClz.getDeclaredField("mOnClickListener");
            mOnClickListener.setAccessible(true);
            View.OnClickListener originOnClickListener = (View.OnClickListener) mOnClickListener.get(listenerInfo);
            // 用自定义的 OnClickListener 替换原始的 OnClickListener
            View.OnClickListener hookedOnClickListener = new HookOnClickListener(originOnClickListener);
            mOnClickListener.set(listenerInfo, hookedOnClickListener);
        } catch (Exception e) {
            Log.w("MainActivity","hook clickListener failed!", e);
        }
    }
}
