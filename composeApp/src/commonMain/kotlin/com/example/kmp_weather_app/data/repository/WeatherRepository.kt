package com.example.kmp_weather_app.data.repository

import com.example.kmp_weather_app.data.local.LocalWeatherDataSource
import com.example.kmp_weather_app.data.network.WeatherApi
import com.example.kmp_weather_app.data.network.toDomain
import com.example.kmp_weather_app.domain.model.Weather

class WeatherRepository(
    private val weatherApi: WeatherApi,
    private val localDataSource: LocalWeatherDataSource
) {
    
    suspend fun getWeatherByCity(city: String): Result<Weather> {
        return try {
            val response = weatherApi.getWeatherByCity(city)
            val weather = response.toDomain()
            localDataSource.saveWeather(weather)
            Result.success(weather)
        } catch (e: Exception) {
            val cachedWeather = localDataSource.getLastWeather()
            if (cachedWeather != null) {
                Result.success(cachedWeather)
            } else {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getLastWeather(): Weather? {
        return localDataSource.getLastWeather()
    }
}
