package com.shop.mobile.locationtracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weather_table")
data class WeatherInfo(
    var cityname: String,
    var datetime: String,
    var temp: Double
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}