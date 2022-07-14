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
import com.shop.mobile.locationtracker.utilities.Resource
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

    private val weatherData = MutableLiveData<Resource<CityData>>()
    private val suggestionData = MutableLiveData<Resource<List<String>>>()
    private val weatherDataByCoordinates = MutableLiveData<Resource<ModelClass>>()

    fun fetchWeatherInformationByCityName(cityName: String) : LiveData<Resource<CityData>> {
        Log.i("Navaneeth","fetchWeatherInformationByCityName")
        weatherData.postValue(Resource.loading(null))
         viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.fetchWeatherByCityName(cityName)
                .catch { e ->
                    Log.e("Navneeth","Unable t"+e.message)
                    weatherData.postValue(Resource.error(e.toString(), null))
                }.collect {
                    Log.i("Navneeth" ,"Server resp " +it.name )
                weatherData.postValue(Resource.success(it))
            }
        }
        return weatherData
    }

    fun fetchWeatherInformationByCoordinates(latitude: String,longitude :String) : LiveData<Resource<ModelClass>> {
        weatherDataByCoordinates.postValue(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.fetchWeatherData(latitude,longitude)
                .catch { e ->
                    Log.e("Navneeth","Unable t"+e.message)
                    weatherDataByCoordinates.postValue(Resource.error(e.toString(), null))
                }.collect {
                    Log.i("Navneeth"," the Resoonse ")
                    weatherDataByCoordinates.postValue(Resource.success(it))
                }
        }
        return weatherDataByCoordinates
    }

    fun fetchCityNameForSuggestion(cityName:String):LiveData<Resource<List<String>>>{
        suggestionData.postValue(Resource.loading(null))
        Log.i("Navneeth","Suggestion List "+cityName)
        viewModelScope.launch (Dispatchers.IO){
           var suggestionlist =  suggestionRepository.fetchWeatherByCityName(cityName)
           suggestionlist.catch {  }.collect{
             var suggestionCitylist = ArrayList<String>();
               if(!it.isNullOrEmpty()) {
                   it.forEach {
                       suggestionCitylist.add(it.cityname)
                   }
                   suggestionData.postValue(Resource.success(suggestionCitylist))
               }
           }
        }
        return suggestionData
    }

}