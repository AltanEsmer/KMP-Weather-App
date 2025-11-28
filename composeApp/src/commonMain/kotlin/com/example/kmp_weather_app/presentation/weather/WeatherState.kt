package com.example.kmp_weather_app.presentation.weather

import com.example.kmp_weather_app.domain.model.Weather

sealed class WeatherState {
    data object Idle : WeatherState()
    data object Loading : WeatherState()
    data class Success(val weather: Weather, val isCached: Boolean = false) : WeatherState()
    data class Error(val message: String) : WeatherState()
}
