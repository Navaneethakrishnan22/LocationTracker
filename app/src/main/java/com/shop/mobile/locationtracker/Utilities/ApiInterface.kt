package com.shop.mobile.locationtracker.Utilities

import android.telecom.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.shop.mobile.locationtracker.POJO.ModelClass

interface ApiInterface  {

    @GET("weather")
    fun getCurrentWeatherData(
        @Query("lat") latitude:String,
        @Query("lon") logitude:String,
        @Query("APPID") api_key:String
    ):Call<com.shop.mobile.locationtracker.POJO.ModelClass>

    @GET("weather")
    fun getCityWeatherData(
        @Query("q") cityName:String,
        @Query("APPID") api_key:String
    ):Call<com.shop.mobile.locationtracker.POJO.ModelClass>


}