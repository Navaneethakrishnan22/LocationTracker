package com.shop.mobile.locationtracker.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.shop.mobile.locationtracker.apiservice.WeatherApiHelperImpl
import com.shop.mobile.locationtracker.apiservice.WeatherApiService
import com.shop.mobile.locationtracker.data.WeatherInfo
import com.shop.mobile.locationtracker.data.dao.WeatherInfoDao
import com.shop.mobile.locationtracker.model.ModelClass
import com.shop.mobile.locationtracker.model.city.CityData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApiService: WeatherApiHelperImpl,
                                            private val weatherInfoDao: WeatherInfoDao){

    suspend fun fetchWeatherData(latitude: String, longitude: String) : Flow<ModelClass> {
        var weatherModelFlow = weatherApiService.getCurrentWeatherData(latitude, longitude)
        weatherModelFlow.collect{
            if(it!=null){
                var weatherInfo = WeatherInfo(it.name,System.currentTimeMillis().toString(),it.main.temp)
                weatherInfoDao.insertWeatherInfoData(weatherInfo);
            }
        }
        return weatherModelFlow
    }

    suspend fun fetchWeatherByCityName(cityName: String) : Flow<CityData> {
        var weatherModelFlow = weatherApiService.getCityWeatherData(cityName)
        Log.i("Navneeth","Resp "+weatherModelFlow)

        weatherModelFlow.collect{
            Log.i("Navneeth","Weather Model "+it)
            if(it!=null){
                Log.i("Navneeth","City Name --->"+it.name)
                var weatherInfo = WeatherInfo(it.name,System.currentTimeMillis().toString(),it.main.temp)
                weatherInfoDao.insertWeatherInfoData(weatherInfo);
            }
        }

        return weatherModelFlow
    }
}