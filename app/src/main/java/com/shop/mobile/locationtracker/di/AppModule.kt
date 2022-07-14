package com.shop.mobile.locationtracker.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.shop.mobile.locationtracker.apiservice.WeatherApiService
import com.shop.mobile.locationtracker.constants.LocationConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(LocationConstants.BASE_URL)
            .client(client)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherAPiService(retrofit: Retrofit): WeatherApiService =
        retrofit.create(WeatherApiService::class.java)

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun providesHttpInterceptor(): HttpLoggingInterceptor {
        var interceptor =  HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build();

    }


}