package com.example.kmp_weather_app.domain.usecase

import com.example.kmp_weather_app.data.repository.WeatherRepository
import com.example.kmp_weather_app.domain.model.Weather

class GetLastWeatherUseCase(private val repository: WeatherRepository) {
    
    suspend operator fun invoke(): Result<Weather?> {
        return try {
            val weather = repository.getLastWeather()
            Result.success(weather)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
