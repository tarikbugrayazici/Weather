package com.example.weather.data.entity

import com.example.weather.data.entity.Condition

data class Current(
    val last_updated_epoch: Int? = null,
    val last_updated: String? = null,
    val temp_c: Double? = null,
    val temp_f: Double? = null,
    val is_day: Int? = null,
    val condition: Condition,
    val wind_mph: Double? = null,
    val wind_kph: Double? = null,
    val wind_degree: Int? = null,
    val wind_dir: String? = null,
    val pressure_mb: Double? = null,
    val pressure_in: Double? = null,
    val precip_mm: Double? = null,
    val precip_in: Double? = null,
    val humidity: Int? = null,
    val cloud: Int? = null,
    val feelslike_c: Double? = null,
    val feelslike_f: Double? = null,
    val vis_km: Double? = null,
    val vis_miles: Double? = null,
    val uv: Double? = null,
    val gust_mph: Double? = null,
    val gust_kph: Double? = null
)