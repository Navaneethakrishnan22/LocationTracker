package com.shop.mobile.locationtracker.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shop.mobile.locationtracker.model.ModelClass
import com.shop.mobile.locationtracker.repository.PersonRepository
import com.shop.mobile.locationtracker.repository.WeatherRepository
import com.shop.mobile.locationtracker.utilities.Resource
import com.shop.mobile.locationtracker.utils.MockData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val personRepository: PersonRepository,
    private val weatherRepository: WeatherRepository

) : ViewModel(){

    private val weatherData = MutableLiveData<Resource<ModelClass>>()

    fun insertPersonData() {
        viewModelScope.launch(Dispatchers.IO) {
            var personList = MockData.getPersonData()
            personList.forEach {
                personRepository.insertData(it)
            }
        }
    }

    fun readPersonData(){
        viewModelScope.launch(Dispatchers.IO) {
            personRepository.readData().collect{
                if(it.isNotEmpty()){
                    it.forEach {
                        Log.i("Navneeth", "City Name " + it.cityname + "TEMP "+it.temp)
                    }
                }
            }
        }
    }

    fun fetchWeatherInformation() : LiveData<Resource<ModelClass>> {
        weatherData.postValue(Resource.loading(null))
         viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.fetchWeatherData(latitude = "12.9716", longitude = "77.5946")
                .catch { e ->
                    Log.e("Navneeth","Unable t"+e.message)
                    weatherData.postValue(Resource.error(e.toString(), null))
                }.collect {
                weatherData.postValue(Resource.success(it))
            }
        }
        return weatherData
    }
}