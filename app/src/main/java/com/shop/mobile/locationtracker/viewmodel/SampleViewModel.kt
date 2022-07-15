package com.shop.mobile.locationtracker.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shop.mobile.locationtracker.model.ModelClass
import com.shop.mobile.locationtracker.model.city.CityData
import com.shop.mobile.locationtracker.repository.SuggestionRepository
import com.shop.mobile.locationtracker.repository.WeatherRepository
import com.shop.mobile.locationtracker.utilities.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val suggestionRepository: SuggestionRepository,
    private val weatherRepository: WeatherRepository

) : ViewModel(){

    private val weatherData = MutableLiveData<ResultData<CityData>>()
    private val suggestionData = MutableLiveData<ResultData<List<String>>>()
    private val weatherDataByCoordinates = MutableLiveData<ResultData<ModelClass>>()

    fun fetchWeatherInformationByCityName(cityName: String) : LiveData<ResultData<CityData>> {
        Log.i("Navaneeth","fetchWeatherInformationByCityName")
        weatherData.postValue(ResultData.loading(null))
         viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.fetchWeatherByCityName(cityName)
                .catch { e ->
                    weatherData.postValue(ResultData.error(e.toString(), null))
                }.collect {
                    Log.i("Navneeth" ,"Server resp " +it.name )
                weatherData.postValue(ResultData.success(it))
            }
        }
        return weatherData
    }

    fun fetchWeatherInformationByCoordinates(latitude: String,longitude :String) : LiveData<ResultData<ModelClass>> {
        weatherDataByCoordinates.postValue(ResultData.loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.fetchWeatherData(latitude,longitude)
                .catch { e ->
                    Log.e("Navneeth","Unable t"+e.message)
                    weatherDataByCoordinates.postValue(ResultData.error(e.toString(), null))
                }.collect {
                    Log.i("Navneeth"," the Resoonse ")
                    weatherDataByCoordinates.postValue(ResultData.success(it))
                }
        }
        return weatherDataByCoordinates
    }

    fun fetchCityNameForSuggestion(cityName:String):LiveData<ResultData<List<String>>>{
        suggestionData.postValue(ResultData.loading(null))
        Log.i("Navneeth","Suggestion List "+cityName)
        viewModelScope.launch (Dispatchers.IO){
           var suggestionlist =  suggestionRepository.fetchWeatherByCityName(cityName)
           suggestionlist.catch {  }.collect{
             var suggestionCitylist = ArrayList<String>();
               if(!it.isNullOrEmpty()) {
                   it.forEach {
                       suggestionCitylist.add(it.cityname)
                   }
                   suggestionData.postValue(ResultData.success(suggestionCitylist))
               }
           }
        }
        return suggestionData
    }

}