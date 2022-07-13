package com.shop.mobile.locationtracker.repository

import com.shop.mobile.locationtracker.apiservice.WeatherApiHelperImpl
import com.shop.mobile.locationtracker.data.Person
import com.shop.mobile.locationtracker.data.PersonDao
import com.shop.mobile.locationtracker.model.ModelClass
import kotlinx.coroutines.flow.Flow
import java.sql.Array
import javax.inject.Inject

class PersonRepository @Inject constructor(private val weatherApiService: WeatherApiHelperImpl,private val personDao: PersonDao) {

    suspend fun fetchWeatherData(latitude: String, longitude: String) : Flow<ModelClass> {
        return weatherApiService.getCurrentWeatherData(latitude, longitude)
    }

    fun readData(): Flow<List<Person>> {
        return personDao.getPersons(personDao.getPersons(arrayOf(String)))
    }

    suspend fun insertData(person: Person) {
        personDao.insertPersonData(person)
    }

    fun searchDatabase(searchQuery: String): Flow<List<Person>> {
        return personDao.searchDatabase(searchQuery)
    }

}