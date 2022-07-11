package com.shop.mobile.locationtracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "person_table")
data class Person(
    var cityname: String,
    var datetime: String,
    var temp: Int
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}