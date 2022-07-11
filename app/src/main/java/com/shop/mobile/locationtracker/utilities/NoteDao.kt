package com.shop.mobile.locationtracker.utilities

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface NoteDao {
    //insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNote(note: Note)

    @Query("SELECT * FROM note_Table")
    fun getAll() : List<Note>
}