package com.shop.mobile.locationtracker.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shop.mobile.locationtracker.R
import com.shop.mobile.locationtracker.databinding.ActivityMainBinding
import com.shop.mobile.locationtracker.viewmodel.MainViewModel
import com.shop.mobile.locationtracker.viewmodel.SampleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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


    }
}