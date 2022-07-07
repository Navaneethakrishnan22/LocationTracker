package com.shop.mobile.locationtracker.utilities

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(tableName = "note_Table")
data class Note (
    var et_get_city_name :String,
    var tv_temp : String,
    var Result : String

)