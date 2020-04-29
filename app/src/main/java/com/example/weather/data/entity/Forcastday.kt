package com.example.weather.data.entity

data class Forcastday(
    val date: String? = null,
    val date_epoch: Int? = null,
    val day: Day? = null,
    val astro: Astro? = null,
    var isSelected: Boolean = false

)