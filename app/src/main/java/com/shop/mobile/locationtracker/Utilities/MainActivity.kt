package com.shop.mobile.locationtracker.Utilities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.audiofx.Equalizer
import android.os.Build
import android.os.Bundle
import android.provider.Contacts.Intents.Insert.ACTION
import android.provider.ContactsContract.Intents.Insert.ACTION
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.databinding.DataBindingUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.shop.mobile.locationtracker.POJO.ModelClass
import com.shop.mobile.locationtracker.R
import com.shop.mobile.locationtracker.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var activityMainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        supportActionBar?.hide()
        activityMainBinding.clMainLayout.visibility=View.GONE

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)

        getCurrentLocation()

}
    private fun getCurrentLocation() {
        if (cheakPermission())
        {
         if (isLocationEnabled())
             {
               if(ActivityCompat.checkSelfPermission(
                       this,
                       android.Manifest.permission.ACCESS_FINE_LOCATION
               ) !=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                       this,
                       android.Manifest.permission.ACCESS_COARSE_LOCATION
               ) !=PackageManager.PERMISSION_GRANTED
               ){
                   requestPermissions()
                   return
               }
                 fusedLocationProviderClient.lastLocation.addOnCompleteListener(this){task->
                     val location: Location?=task.result
                     if (location==null)
                     {
                         Toast.makeText(this,"Not Recieved", Toast.LENGTH_SHORT).show()
                     }
                     else{
                           fetchCurrentLocationWeather(location.latitude.toString(),location.latitude.toString())
                     }
                 }
             }
        }else
        {
            Toast.makeText(this,"Turn on Location", Toast.LENGTH_SHORT).show()
            val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    private fun fetchCurrentLocationWeather(latitude: String, langitude: String) {
        activityMainBinding.pbLoading.Visibility = View.VISIBLE
        ApiUtilities.getApiInterface()?.getCurrentWeatherData(latitude, langitude, API_KEY)
            ?.enqueue(object :
                Callback<ModelClass> {
                override fun onResponse(call: Call<ModelClass>, response: Response<ModelClass>) {
                    if (response.isSuccessful) {
                        setDataOnViews(response.body())
                    }

                }

                override fun onFailure(call: Call<ModelClass>, t: Throwable) {
                    Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
                }
            })

    }

    private fun setDataOnViews(body: ModelClass?) {
        val sdf=SimpleDateFormat("dd/mm/yyyy hh:mm")
        val currentDate=sdf.format(Date())
        activityMainBinding.tvDateAndTime.text=currentDate

        activityMainBinding.tvDayMaxTemp.text=  "Day " + kelvinToCelcius(body!!.main.temp_max) + "°"
        activityMainBinding.tvDayMaxTemp.text=  "Day " + kelvinToCelcius(body!!.main.temp_min) + "°"
        activityMainBinding.tvTemp.text=  "" + kelvinToCelcius(body!!.main.temp) +   "°"
        activityMainBinding.tvFeelsAlike.text=  "" + kelvinToCelcius(body!!.main.feels_like) + "°"
        activityMainBinding.tvWeatherType.text=  body.weather[0].main
        activityMainBinding.tvSunrise.text= timeStampToLocalDate(body.sys.sunrise.toLong())
        activityMainBinding.tvSunset.text= timeStampToLocalDate(body.sys.sunset.toLong())
        activityMainBinding.tvPressure.text=body.main.pressure.toString()
        activityMainBinding.tvHumidity.text=body.main.humidity.toString() +"%"
        activityMainBinding.tvWindSpeed.text=body.wind.speed.toString() +" m/s "

        activityMainBinding.tvTempFarenheat.text=""+((kelvinToCelcius(body.main.temp)).times(1.8).plus(32).roundToInt())
        activityMainBinding.etGetCityName.setText(body.name)


        UpdateUI(body.weather[0].id)







    }
    private fun UpdateUI(id: Int) {
        if(id in 200..232)
        {
            //thunderstrom
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.thunderStrom)
            activityMainBinding.clToolBar.setBackgroundColor(resources.getColor(R.color.thunderStrom))
            activityMainBinding.clSubLayout.background = ContextCompat.getDrawable(
                this@MainActivity,
                 R.drawable.thunderstrom_background
            )
            activityMainBinding.llMainBgBelow.background = ContextCompat.getDrawable(
                this@MainActivity,
                 R.drawable.thunderstrom_background

            )
            activityMainBinding.llMainBgAbove.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.thunderstrom_background

            )
            activityMainBinding.ivWeatherBg.setImageResource(R.drawable.thunderstrom_background)
            activityMainBinding.ivWeatherIcon.setImageResource(R.drawable.rainyicon)

       } else if (id in 300..321) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor=resources.getColor(R.color.drizzle)
            activityMainBinding.clToolBar.setBackgroundColor(resources.getColor(R.color.drizzle))
            activityMainBinding.clSubLayout.background = ContextCompat.getDrawable(
                this@MainActivity,
            R.drawable.rainy_background
            )
            activityMainBinding.llMainBgAbove.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.rainy_background

            )
        }else if (id in 500..531) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor=resources.getColor(R.color.drizzle)
            activityMainBinding.clToolBar.setBackgroundColor(resources.getColor(R.color.drizzle))
            activityMainBinding.clSubLayout.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.rainy_background
            )
            activityMainBinding.llMainBgAbove.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.rainy_background

            )
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun timeStampToLocalDate(timeStamp: Long): String{
        val localTime= timeStamp.let {
            Instant.ofEpochMilli(it)
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
        }
        return localTime.toString()
    }

    private fun kelvinToCelcius(temp: Double): Double {
        var intTemp = temp
        intTemp = intTemp.minus(273)
        return intTemp.toBigDecimal().setScale(1,RoundingMode.UP).toDouble()
    }

    private fun isLocationEnabled() { }

    private fun requestPermissions() { }

    companion object{
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
        const val API_KEY= "dab3af44de7d24ae7ff86549334e45bd"
    }

    private fun cheakPermission(): Boolean
    { }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}


