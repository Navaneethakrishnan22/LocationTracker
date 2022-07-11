package com.shop.mobile.locationtracker.utilities


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note_Table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var et_get_city_name: String,
    var tv_date_and_time:String,
    var tv_temp: String,
    var Result:String
)