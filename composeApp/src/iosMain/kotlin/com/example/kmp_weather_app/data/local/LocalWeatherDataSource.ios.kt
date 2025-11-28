package com.example.kmp_weather_app.data.local

import com.example.kmp_weather_app.domain.model.Weather

class LocalWeatherDataSourceImpl : LocalWeatherDataSource {
    
    // Placeholder implementation - will be replaced with actual storage in Phase 2
    private var cachedWeather: Weather? = null
    
    override suspend fun saveWeather(weather: Weather) {
        cachedWeather = weather
    }
    
    override suspend fun getLastWeather(): Weather? {
        return cachedWeather
    }
}

actual fun createLocalWeatherDataSource(): LocalWeatherDataSource = LocalWeatherDataSourceImpl()
