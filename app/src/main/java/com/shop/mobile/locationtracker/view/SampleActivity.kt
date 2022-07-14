package com.shop.mobile.locationtracker.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.shop.mobile.locationtracker.R
import com.shop.mobile.locationtracker.databinding.ActivityMainBinding
import com.shop.mobile.locationtracker.databinding.ActivitySampleBinding
import com.shop.mobile.locationtracker.utilities.Status
import com.shop.mobile.locationtracker.viewmodel.MainViewModel
import com.shop.mobile.locationtracker.viewmodel.SampleViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class SampleActivity : AppCompatActivity() {
    private lateinit var activtysamplebinding: ActivitySampleBinding
    private val sampleViewModel: SampleViewModel by viewModels()
    private lateinit  var autoSuggestionView:AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activtysamplebinding = DataBindingUtil.setContentView(this, R.layout.activity_sample)
        initializeAutoComplete()
    }

    private fun initializeAutoComplete() {
        autoSuggestionView = activtysamplebinding.autoTextView
        autoSuggestionView.apply {
            threshold = 1
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(editable: Editable?) {
                    getSuggestionForCity(editable.toString())
                }
            })
        }
    }

    private fun fetchWeatherBasedonCity(){
        activtysamplebinding.apply {
            btn.setOnClickListener {
              var  autotext =  activtysamplebinding.autoTextView.text.toString()
               Log.i("Navneeth","Entered Text "+autotext)
                getSuggestionForCity("c")
            }
        }
    }

    private fun setupWeatherObserver() {
        sampleViewModel.fetchWeatherInformationByCoordinates("","").observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.i("Navneeth", "THe Weather Data " + it.data!!.name)
                }
                Status.LOADING -> {
                   // Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    //Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    private fun getWeatherDataByCity(cityName:String){
        sampleViewModel.fetchWeatherInformationByCoordinates("8.9564","77.3152").observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.i("Navneeth", "THe Weather Data " + it.data!!.name)
                    activtysamplebinding.weatherResult.text = "CityNAme "+ it.data!!.name + " Temp "+it.data?.main?.temp
                }
                Status.LOADING -> {
                    // Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    //Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getSuggestionForCity(search:String){
        var suggestionList = sampleViewModel.fetchCityNameForSuggestion(search)
        suggestionList.observe(this, Observer {
            when(it.status){
                Status.ERROR->{
                    Log.i("Navneeth","Suggestion ERROR "+it.message)
                }
                Status.SUCCESS->{
                    if(it.data!=null) {
                        plugSuggestion(it.data!!)
                    }
                }
                Status.LOADING->{

                }
            }
        })
    }

    private fun plugSuggestion(suggestionList:List<String>){
        var suggestionadapters = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, suggestionList)
        autoSuggestionView.setAdapter(suggestionadapters)
        suggestionadapters.notifyDataSetChanged()
    }
}