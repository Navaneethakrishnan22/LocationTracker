package com.shop.mobile.locationtracker.pojo

import com.google.gson.annotations.SerializedName
import com.shop.mobile.locationtracker.POJO.Sys
import com.shop.mobile.locationtracker.POJO.Wind


data class ModelClass(

    @SerializedName("weather") val weather:List<Weather>,
    @SerializedName("main") val main:Main,
    @SerializedName("wind") val wind: Wind,
    @SerializedName("sys") val sys: Sys,
    @SerializedName("id") val id:Int,
    @SerializedName("name") val name:String




)

