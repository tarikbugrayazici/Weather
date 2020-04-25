package com.example.weather.data.entity

data class Location(
    val name: String? = null,
    val region: String? = null,
    val country: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val tz_id: String? = null,
    val localtime_epoch: Int? = null,
    val localtime: String? = null
)