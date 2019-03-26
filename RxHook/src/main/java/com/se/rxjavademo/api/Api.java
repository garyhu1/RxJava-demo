package com.se.rxjavademo.api;

import com.se.rxjavademo.pojo.LocationBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Author : garyhu
 * Since : 2019/3/7
 */

public interface Api {

    @GET("map/v1/LUOKUANGOS/get_locate_icon")
    Call<LocationBean> getLocationIcon();
}
