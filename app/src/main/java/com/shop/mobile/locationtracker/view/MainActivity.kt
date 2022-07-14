package com.shop.mobile.locationtracker.view

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.shop.mobile.locationtracker.R
import com.shop.mobile.locationtracker.Utilities.ApiUtilities

import com.shop.mobile.locationtracker.databinding.ActivityMainBinding
import com.shop.mobile.locationtracker.db.AppDatabase
import com.shop.mobile.locationtracker.model.ModelClass
import com.shop.mobile.locationtracker.utilities.Resource
import com.shop.mobile.locationtracker.viewmodel.SampleViewModel
import com.shop.mobile.locationtracker.viewmodel.MainViewModel

import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*
import kotlin.math.roundToInt

private val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity()  {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var activityMainBinding: ActivityMainBinding
    private val sampleViewModel: SampleViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val weatherData = MutableLiveData<Resource<ModelClass>>()
    private val appDatabase = AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.hide()
        activityMainBinding.clMainLayout.visibility = View.GONE
        fetchCurrentLocationWeather("12.9716", "77.5946");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

      //  sampleViewModel.insertPersonData()
        //sampleViewModel.readPersonData()

       // getCity();
    }


   /* private fun getCity()
    {
        activityMainBinding.etGetCityName.setOnEditorActionListener({ v, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        getCityWeather(activityMainBinding.etGetCityName.text.toString())
                        val view = this.currentFocus
                        if (view != null) {
                            val imm: InputMethodManager =
                                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(view.windowToken, 0)
                            activityMainBinding.etGetCityName.clearFocus()

                        }

                }
                true

            } else false

        })*/



    private fun getCityWeather(cityName: String)  {
        ApiUtilities.getApiInterface()?.getCityWeatherData(cityName, API_KEY)
            ?.enqueue(object : Callback<ModelClass> {
                override fun onResponse(call: Call<ModelClass>, response: Response<ModelClass>) {
                    setDataOnViews(response.body())
                }

                override fun onFailure(call: Call<ModelClass>, t: Throwable) {
                    Toast.makeText(applicationContext, "Not a Valid City Name", Toast.LENGTH_SHORT)
                        .show()

                }
            })

    }

    private fun getCurrentLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions()
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        Toast.makeText(this, "Null Received", Toast.LENGTH_SHORT).show()
                    } else {
                        fetchCurrentLocationWeather(
                            location.latitude.toString(),
                            location.latitude.toString()
                        )
                    }
                }
            } else {
                Toast.makeText(this, "Turn on Location", Toast.LENGTH_SHORT).show()
                val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }

    }

    private fun fetchCurrentLocationWeather(latitude: String, langitude: String) {
        activityMainBinding.progressLoading.visibility = View.VISIBLE
        ApiUtilities.getApiInterface()?.getCurrentWeatherData(latitude, langitude, API_KEY)
            ?.enqueue(object :
                Callback<ModelClass> {
                override fun onResponse(call: Call<ModelClass>, response: Response<ModelClass>) {
                    Log.i("NAVNEETH", "on Response " + response.body())
                    if (response.isSuccessful) {
                        setDataOnViews(response.body())
                    }

                }

                override fun onFailure(call: Call<ModelClass>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }
            })

    }

    private fun setDataOnViews(body: ModelClass?) {
        val sdf = SimpleDateFormat("dd/mm/yyyy hh:mm")
        val currentDate = sdf.format(Date())
        activityMainBinding.tvDateAndTime.text = currentDate

        activityMainBinding.tvDayMaxTemp.text = "Day " + kelvinToCelcius(body!!.main.temp_max) + "°"
        activityMainBinding.tvDayMinTemp.text = "Day " + kelvinToCelcius(body!!.main.temp_min) + "°"
        activityMainBinding.tvTemp.text = "" + kelvinToCelcius(body!!.main.temp) + "°"
        activityMainBinding.tvFeelsAlike.text = "" + kelvinToCelcius(body!!.main.feels_like) + "°"
        activityMainBinding.tvWeatherType.text = body.weather[0].main
        activityMainBinding.tvSunrise.text = timeStampToLocalDate(body.sys.sunrise.toLong())
        activityMainBinding.tvSunset.text = timeStampToLocalDate(body.sys.sunset.toLong())
        activityMainBinding.tvPressure.text = body.main.pressure.toString()
        activityMainBinding.tvHumidity.text = body.main.humidity.toString() + "%"
        activityMainBinding.tvWindSpeed.text = body.wind.speed.toString() + " m/s "

        activityMainBinding.tvTempFarenheat.text =
            "" + ((kelvinToCelcius(body.main.temp)).times(1.8).plus(32).roundToInt())
        activityMainBinding.etGetCityName.setText(body.name)


        UpdateUI(body.weather[0].id)


    }

    private fun UpdateUI(id: Int) {
        if (id in 200..232) {
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
            activityMainBinding.ivWeatherIcon.setImageResource(R.drawable.thunder)

        } else if (id in 300..321) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.drizzle)
            activityMainBinding.clToolBar.setBackgroundColor(resources.getColor(R.color.drizzle))
            activityMainBinding.clSubLayout.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.drizzle_background
            )
            activityMainBinding.llMainBgAbove.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.drizzle_background

            )
            activityMainBinding.ivWeatherBg.setImageResource(R.drawable.drizzle_background)
            activityMainBinding.ivWeatherIcon.setImageResource(R.drawable.drizzle)

        } else if (id in 500..531) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.rain)
            activityMainBinding.clToolBar.setBackgroundColor(resources.getColor(R.color.rain))
            activityMainBinding.clSubLayout.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.rainy_background
            )
            activityMainBinding.llMainBgBelow.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.rainy_background

            )
            activityMainBinding.ivWeatherBg.setImageResource(R.drawable.rainy_background)
            activityMainBinding.ivWeatherIcon.setImageResource(R.drawable.rainyicon)

        } else if (id in 600..620) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.snow)
            activityMainBinding.clToolBar.setBackgroundColor(resources.getColor(R.color.snow))
            activityMainBinding.clSubLayout.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.snow_background
            )
            activityMainBinding.llMainBgBelow.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.snow_background

            )
            activityMainBinding.ivWeatherBg.setImageResource(R.drawable.snow_background)
            activityMainBinding.ivWeatherIcon.setImageResource(R.drawable.misticon)

        } else if (id in 701..781) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.mist)
            activityMainBinding.clToolBar.setBackgroundColor(resources.getColor(R.color.mist))
            activityMainBinding.clSubLayout.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.mist_background
            )
            activityMainBinding.llMainBgBelow.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.mist_background

            )
            activityMainBinding.ivWeatherBg.setImageResource(R.drawable.mist_background)
            activityMainBinding.ivWeatherIcon.setImageResource(R.drawable.misticon)

        } else if (id in 701..781) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.mist)
            activityMainBinding.clToolBar.setBackgroundColor(resources.getColor(R.color.mist))
            activityMainBinding.clSubLayout.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.mist_background
            )
            activityMainBinding.llMainBgBelow.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.mist_background

            )
            activityMainBinding.ivWeatherBg.setImageResource(R.drawable.mist_background)
            activityMainBinding.ivWeatherIcon.setImageResource(R.drawable.misticon)

        } else if (id == 800) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.clear)
            activityMainBinding.clToolBar.setBackgroundColor(resources.getColor(R.color.clear))
            activityMainBinding.clSubLayout.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.clear_sky_bacground
            )
            activityMainBinding.llMainBgBelow.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.clear_sky_bacground

            )
            activityMainBinding.ivWeatherBg.setImageResource(R.drawable.clear_sky_bacground)
            activityMainBinding.ivWeatherIcon.setImageResource(R.drawable.clearskyicon)

        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.ash)
            activityMainBinding.clToolBar.setBackgroundColor(resources.getColor(R.color.ash))
            activityMainBinding.clSubLayout.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.cloud_background
            )
            activityMainBinding.llMainBgBelow.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.cloud_background
            )
            activityMainBinding.ivWeatherBg.setImageResource(R.drawable.cloud_background)
            activityMainBinding.ivWeatherIcon.setImageResource(R.drawable.cloudyicon)

        }
        activityMainBinding.progressLoading.visibility = View.GONE
        activityMainBinding.clMainLayout.visibility = View.VISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun timeStampToLocalDate(timeStamp: Long): String {
        val localTime = timeStamp.let {
            Instant.ofEpochMilli(it)
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
        }
        return localTime.toString()
    }

    private fun kelvinToCelcius(temp: Double): Double {
        var intTemp = temp
        intTemp = intTemp.minus(273)
        return intTemp.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )

    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
        const val API_KEY = "dab3af44de7d24ae7ff86549334e45bd"
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION

            )
            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}

