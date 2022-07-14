package com.shop.mobile.locationtracker.model.city

import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lon") val lon : Double,
    @SerializedName("lat") val lat : Double)

