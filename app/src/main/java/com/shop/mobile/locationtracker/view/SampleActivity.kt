package com.shop.mobile.locationtracker.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.shop.mobile.locationtracker.R
import com.shop.mobile.locationtracker.databinding.ActivityMainBinding
import com.shop.mobile.locationtracker.utilities.Status
import com.shop.mobile.locationtracker.viewmodel.MainViewModel
import com.shop.mobile.locationtracker.viewmodel.SampleViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
@HiltViewModel
class SampleActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val sampleViewModel: SampleViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.hide()
        activityMainBinding.clMainLayout.visibility = View.VISIBLE
        sampleViewModel.insertPersonData()
        sampleViewModel.readPersonData()
        setupWeatherObserver();
    }

    private fun setupWeatherObserver() {
        sampleViewModel.fetchWeatherInformation().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.i("Navneeth", "THe Weather Data " + it.data!!.name)
                }
                Status.LOADING -> {
                    Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}