package com.shop.mobile.locationtracker.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.shop.mobile.locationtracker.data.Person
import com.shop.mobile.locationtracker.repository.PersonRepository
import com.shop.mobile.locationtracker.utils.MockData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val personRepository: PersonRepository
) : ViewModel() {


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