package com.shop.mobile.locationtracker.Utilities

import android.telecom.PhoneAccount.builder
import com.google.android.material.shape.ShapeAppearanceModel.builder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.stream.DoubleStream.builder
import java.util.stream.IntStream.builder
import java.util.stream.LongStream.builder
import java.util.stream.Stream.builder

object ApiUtilities {
    private var retrofit: Retrofit? = null
    var BASE_URL="https://api.openweathermap.org/data/2.5/weather?lat=";
    fun getApiInterface():ApiInterface?{
        if (retrofit==null)
        {
            retrofit=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit!!.create(ApiInterface::class.java)
    }
}
