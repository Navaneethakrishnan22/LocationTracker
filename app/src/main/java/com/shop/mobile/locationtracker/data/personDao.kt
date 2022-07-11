package com.shop.mobile.locationtracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonData(person: Person)

    @Query("SELECT * FROM person_table WHERE cityname LIKE :searchQuery OR datetime LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<Person>>

    @Query("DELETE FROM person_table")
    suspend fun deleteFromPerson()

    @Transaction
    suspend fun updatePerson(person: Person) {
        person.let {
            deleteFromPerson()
            insertPersonData(it)
        }
    }

    @Query("SELECT * FROM person_table")
    fun getPersons(): Flow<List<Person>>
}
