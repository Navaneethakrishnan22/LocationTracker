package com.shop.mobile.locationtracker.utilities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface NoteDao {
    //insert
    @Insert
    fun insert(note: Note)

    //update
    @Update
    fun update(note: Note)

    //fetch
    @Query("SELECT * FROM note_Table")
    fun getAllNotes():List<Note>
}