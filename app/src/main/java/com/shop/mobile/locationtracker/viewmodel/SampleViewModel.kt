package com.shop.mobile.locationtracker.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shop.mobile.locationtracker.repository.PersonRepository
import com.shop.mobile.locationtracker.utils.MockData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val personRepository: PersonRepository

) : ViewModel(){
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
}