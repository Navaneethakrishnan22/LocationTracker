package com.shop.mobile.locationtracker.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
// it's repository

/*
here we have basically injected our person Dao for our dao interface
and having three function as well........ */
class Repository @Inject constructor(
    private val personDao: PersonDao
) {

    fun readData(): Flow<List<Person>> {
        return personDao.readData()
    }

    suspend fun insertData(person: Person) {
        personDao.insertData(person)
    }

    fun searchDatabase(searchQuery: String): Flow<List<Person>> {
        return personDao.searchDatabase(searchQuery)
    }

}