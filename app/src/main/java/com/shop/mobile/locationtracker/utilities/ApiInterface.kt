package com.shop.mobile.locationtracker.utilities


import com.shop.mobile.locationtracker.pojo.ModelClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface  {

    @GET("weather")
    fun getCurrentWeatherData(
        @Query("lat") latitude:String,
        @Query("lon") logitude:String,
        @Query("APPID") api_key:String
    ): Call<ModelClass>

    @GET("weather")
    fun getCityWeatherData(
        @Query("q") cityName:String,
        @Query("APPID") api_key:String
    ):Call<ModelClass>


}