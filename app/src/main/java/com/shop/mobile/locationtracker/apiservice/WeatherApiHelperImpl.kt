package com.shop.mobile.locationtracker.apiservice

import com.shop.mobile.locationtracker.constants.LocationConstants
import com.shop.mobile.locationtracker.model.ModelClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherApiHelperImpl @Inject constructor(private val  weatherApiService: WeatherApiService) : WeatherApiHelper {

    override suspend fun getCurrentWeatherData(latitude: String, longitude: String,): Flow<ModelClass> {
        return flow { emit(weatherApiService.getCurrentWeatherData(latitude,longitude,LocationConstants.API_KEY)) }
    }

    override suspend fun getCityWeatherData(cityName: String): Flow<ModelClass> {
        return flow { weatherApiService.getCityWeatherData(cityName,LocationConstants.API_KEY) }
    }

}