package com.shop.mobile.locationtracker.POJO

import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("type") val type: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("country") val country: String,
    @SerializedName("Sunrise") val sunrise: Int,
    @SerializedName("sunset") val sunset: Int

)
