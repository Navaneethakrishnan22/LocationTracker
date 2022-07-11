package com.shop.mobile.locationtracker.apps

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LocationApp :Application(){

    override fun onCreate() {
        super.onCreate()
        Log.i("Navneeth","Location App ");
    }

}