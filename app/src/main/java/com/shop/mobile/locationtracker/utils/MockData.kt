package com.shop.mobile.locationtracker.utils

import com.shop.mobile.locationtracker.data.WeatherInfo

object MockData {

    fun getPersonData() : ArrayList<WeatherInfo>{
        var personlist = ArrayList<WeatherInfo>()
        var weatherInfo1 =WeatherInfo("Chennai",System.currentTimeMillis().toString(),50.0);
        var weatherInfo2 =WeatherInfo("Bangalore",System.currentTimeMillis().toString(),35.0);
        personlist.add(weatherInfo1)
        personlist.add(weatherInfo2)
        return personlist
    }
}

