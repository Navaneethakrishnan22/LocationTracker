package com.shop.mobile.locationtracker.apiservice

import com.shop.mobile.locationtracker.model.ModelClass
import com.shop.mobile.locationtracker.model.city.CityData
import kotlinx.coroutines.flow.Flow


interface WeatherApiHelper {

    suspend fun getCurrentWeatherData(latitude: String,longitude: String) : Flow<ModelClass>

    suspend fun getCityWeatherData(cityName:String):Flow<CityData>
}