package com.shop.mobile.locationtracker.data.dao

import androidx.room.*
import com.shop.mobile.locationtracker.data.WeatherInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherInfoData(weatherInfo: WeatherInfo)

    @Query("SELECT * FROM weather_table WHERE cityname LIKE :searchQuery OR datetime LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<WeatherInfo>>

    @Query("DELETE FROM weather_table")
    suspend fun deleteFromWeatherInfo()

    @Transaction
    suspend fun updatePerson(weatherInfo: WeatherInfo) {
        weatherInfo.let {
            deleteFromWeatherInfo()
            insertWeatherInfoData(it)
        }
    }

    @Query("SELECT * FROM weather_table")
    fun getAllWeatherInfo(): Flow<List<WeatherInfo>>

    @Query("SELECT * FROM weather_table where cityname LIKE '%' || :search || '%'")
    fun getSuggestionOnTyping(search:String) : Flow<List<WeatherInfo>>

}