package com.example.weather.ui.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.entity.Forcastday
import com.example.weather.ui.WeatherRepository

class MainActivityViewModel : ViewModel() {

    var weatherLiveData = MutableLiveData<ArrayList<Forcastday>>()
    private var weatherRepository = WeatherRepository()

    fun getLastWeather() {
        weatherRepository.getWeatherForecast("California") { list ->
            weatherLiveData.value = list
        }

    }
}