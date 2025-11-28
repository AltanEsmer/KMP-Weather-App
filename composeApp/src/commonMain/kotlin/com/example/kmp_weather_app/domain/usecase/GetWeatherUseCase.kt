package com.example.kmp_weather_app.domain.usecase

import com.example.kmp_weather_app.data.repository.WeatherRepository
import com.example.kmp_weather_app.domain.model.Weather

class GetWeatherUseCase(private val repository: WeatherRepository) {
    
    suspend operator fun invoke(city: String): Result<Weather> {
        if (city.isBlank()) {
            return Result.failure(IllegalArgumentException("City name cannot be empty"))
        }
        return repository.getWeatherByCity(city)
    }
}
