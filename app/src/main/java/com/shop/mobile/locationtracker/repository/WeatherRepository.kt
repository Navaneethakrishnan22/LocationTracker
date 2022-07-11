package com.shop.mobile.locationtracker.repository

import androidx.lifecycle.MutableLiveData
import com.shop.mobile.locationtracker.apiservice.WeatherApiHelperImpl
import com.shop.mobile.locationtracker.apiservice.WeatherApiService
import com.shop.mobile.locationtracker.model.ModelClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApiService: WeatherApiHelperImpl){

    suspend fun fetchWeatherData(latitude: String, longitude: String) : Flow<ModelClass> {
        return weatherApiService.getCurrentWeatherData(latitude, longitude)
    }
}