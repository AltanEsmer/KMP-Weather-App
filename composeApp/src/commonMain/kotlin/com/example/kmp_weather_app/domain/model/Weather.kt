package com.example.kmp_weather_app.domain.model

data class Weather(
    val cityName: String,
    val temperature: Double,
    val description: String,
    val humidity: Int,
    val windSpeed: Double
)
