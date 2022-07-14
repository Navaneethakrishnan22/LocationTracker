package com.shop.mobile.locationtracker.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shop.mobile.locationtracker.repository.SuggestionRepository
import com.shop.mobile.locationtracker.repository.WeatherRepository
import com.shop.mobile.locationtracker.utils.MockData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val suggestionRepository: SuggestionRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {


}