package com.shop.mobile.locationtracker.utilities

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface NoteDao {
    //insert
    @Insert
    suspend fun addNote(note: Note)

    /*update
    @Update
    fun update(note: Note)*/

    //fetch
    @Query("SELECT * FROM note_Table WHERE et_get_city_name & tv_temp LIKE :et_get_city_name LIMIT 1")
    suspend fun getAllNote(et_get_city_name: String) : Note
}