package com.example.kmp_weather_app.data.local

import com.example.kmp_weather_app.domain.model.Weather

interface LocalWeatherDataSource {
    suspend fun saveWeather(weather: Weather)
    suspend fun getLastWeather(): Weather?
}

expect fun createLocalWeatherDataSource(): LocalWeatherDataSource
