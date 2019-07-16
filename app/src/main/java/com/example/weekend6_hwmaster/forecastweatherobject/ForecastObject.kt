package com.example.weekend6_hwmaster.forecastweatherobject

data class ForecastObject(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<X>,
    val message: Double
)