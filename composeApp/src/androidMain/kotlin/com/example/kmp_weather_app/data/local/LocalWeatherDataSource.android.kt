package com.example.kmp_weather_app.data.local

import com.example.kmp_weather_app.domain.model.Weather

class LocalWeatherDataSourceImpl(
    private val weatherDao: WeatherDao
) : LocalWeatherDataSource {
    
    override suspend fun saveWeather(weather: Weather) {
        try {
            weatherDao.insertWeather(weather.toEntity())
        } catch (e: Exception) {
            throw Exception("Failed to save weather to database: ${e.message}")
        }
    }
    
    override suspend fun getLastWeather(): Weather? {
        return try {
            weatherDao.getLastWeather()?.toDomain()
        } catch (e: Exception) {
            null
        }
    }
}

actual fun createLocalWeatherDataSource(): LocalWeatherDataSource {
    throw IllegalStateException("Use createLocalWeatherDataSource(context) on Android")
}

