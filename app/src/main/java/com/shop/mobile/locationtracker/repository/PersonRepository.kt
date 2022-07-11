package com.shop.mobile.locationtracker.repository

import com.shop.mobile.locationtracker.data.Person
import com.shop.mobile.locationtracker.data.PersonDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonRepository @Inject constructor(
    private val personDao: PersonDao
) {

    fun readData(): Flow<List<Person>> {
        return personDao.getPersons()
    }

    suspend fun insertData(person: Person) {
        personDao.insertPersonData(person)
    }

    fun searchDatabase(searchQuery: String): Flow<List<Person>> {
        return personDao.searchDatabase(searchQuery)
    }

}