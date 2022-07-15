package com.shop.mobile.locationtracker.model.city

import Main
import Sys
import Weather
import Wind
import com.google.gson.annotations.SerializedName

 data class CityData(@SerializedName("coord") val coord : Coord,
                    @SerializedName("weather") val weather : List<Weather>,
                    @SerializedName("base") val base : String="",
                    @SerializedName("main") val main : Main,
                    @SerializedName("visibility") val visibility : Int=0,
                    @SerializedName("wind") val wind : Wind,
                    @SerializedName("clouds") val clouds : Clouds,
                    @SerializedName("dt") val dt : Int,
                    @SerializedName("sys") val sys : Sys,
                    @SerializedName("timezone") val timezone : Int=0,
                    @SerializedName("id") val id : Int=0,
                    @SerializedName("name") val name : String="",
                    @SerializedName("cod") val cod : Int=0,
                    @SerializedName("message") val message:String=""
) :Response()

data class Error(var message: String):Response(){

}

open class Response()
