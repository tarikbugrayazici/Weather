package com.example.weather.ui

import com.example.weather.data.Constants
import com.example.weather.data.entity.BaseEntity
import com.example.weather.data.entity.Forcastday
import com.example.weather.data.service.API
import com.example.weather.data.service.RetrofitService
import retrofit2.Response

class WeatherRepository() {

    private var weather = ArrayList<Forcastday>()

    fun getWeatherForecast(cityName: String, block: (ArrayList<Forcastday>?) -> Unit) {
        val retrofit: API = RetrofitService.getRetrofit()!!.create(API::class.java)
        val call = retrofit.getWeather(Constants.API_KEY, cityName!!, 7)
        call.enqueue(object : retrofit2.Callback<BaseEntity> {
            override fun onResponse(
                call: retrofit2.Call<BaseEntity>,
                response: Response<BaseEntity>
            ) {
                block(response.body()!!.forecast!!.forecastday)
            }

            override fun onFailure(call: retrofit2.Call<BaseEntity>, t: Throwable) {
                System.out.println("hata" + t)
            }
        })
    }
}