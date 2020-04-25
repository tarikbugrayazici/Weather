package com.example.weather.data.service


import com.example.weather.data.entity.BaseEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("forecast.json")
    fun getWeather(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("days") days: Int
    ): Call<BaseEntity>
}