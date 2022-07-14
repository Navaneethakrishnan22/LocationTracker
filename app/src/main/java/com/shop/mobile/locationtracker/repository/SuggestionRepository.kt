package com.shop.mobile.locationtracker.repository

import android.util.Log
import com.shop.mobile.locationtracker.data.WeatherInfo
import com.shop.mobile.locationtracker.data.dao.WeatherInfoDao
import com.shop.mobile.locationtracker.model.ModelClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SuggestionRepository @Inject constructor(private val  weatherInfoDao: WeatherInfoDao){

    suspend fun fetchWeatherByCityName(cityName: String) : Flow<List<WeatherInfo>>{
        Log.i("Navneeth","City Name "+cityName)
        var cityListFlow =  weatherInfoDao.getSuggestionOnTyping(cityName)
        return cityListFlow
    }

}