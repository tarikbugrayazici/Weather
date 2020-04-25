package com.example.weather.data.entity

data class Day(
    val maxtemp_c: Double? = null,
    val maxtemp_f: Double? = null,
    val mintemp_c: Double? = null,
    val mintemp_f: Double? = null,
    val avgtemp_c: Double? = null,
    val avgtemp_f: Double? = null,
    val maxwind_mph: Double? = null,
    val maxwind_kph: Double? = null,
    val totalprecip_mm: Double? = null,
    val totalprecip_in: Double? = null,
    val avgvis_km: Double? = null,
    val avgvis_miles: Double? = null,
    val avghumidity: Double? = null,
    val condition: Condition_? = null,
    val uv: Double? = null
)