package com.shop.mobile.locationtracker.apiservice

import com.shop.mobile.locationtracker.model.ModelClass
import com.shop.mobile.locationtracker.model.city.CityData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    suspend fun getCurrentWeatherData(
        @Query("lat") latitude: String,
        @Query("lon") logitude: String,
        @Query("APPID") api_key: String
    ): ModelClass

    @GET("weather")
    suspend fun getCityWeatherData(
        @Query("q") cityName: String,
        @Query("APPID") api_key: String
    ): CityData
}