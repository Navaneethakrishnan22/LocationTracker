package com.shop.mobile.locationtracker.repository

import com.shop.mobile.locationtracker.apiservice.WeatherApiHelperImpl
import com.shop.mobile.locationtracker.data.dao.WeatherInfoDao
import com.shop.mobile.locationtracker.model.ModelClass
import kotlinx.coroutines.flow.Flow
import java.sql.Array
import javax.inject.Inject

class PersonRepository @Inject constructor(private val weatherApiService: WeatherApiHelperImpl,private val personDao: WeatherInfoDao) {

    suspend fun fetchWeatherData(latitude: String, longitude: String) : Flow<ModelClass> {
        return weatherApiService.getCurrentWeatherData(latitude, longitude)
    }



}