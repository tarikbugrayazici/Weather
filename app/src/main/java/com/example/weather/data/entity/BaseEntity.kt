package com.example.weather.data.entity


data class BaseEntity(
    val location: Location? = null,
    val current: Current? = null,
    val forecast: Forecast? = null,
    val alert: Alert? = null
)
