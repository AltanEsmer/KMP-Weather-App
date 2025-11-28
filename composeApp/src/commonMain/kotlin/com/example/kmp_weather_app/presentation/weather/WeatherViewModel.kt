package com.example.kmp_weather_app.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmp_weather_app.domain.usecase.GetLastWeatherUseCase
import com.example.kmp_weather_app.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getLastWeatherUseCase: GetLastWeatherUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val state: StateFlow<WeatherState> = _state.asStateFlow()

    init {
        loadLastWeather()
    }

    private fun loadLastWeather() {
        viewModelScope.launch {
            getLastWeatherUseCase()
                .onSuccess { weather ->
                    weather?.let {
                        _state.value = WeatherState.Success(it, isCached = true)
                    }
                }
                .onFailure {
                    // Silently fail - no cached data available
                }
        }
    }

    fun fetchWeather(city: String, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _state.value = WeatherState.Loading

            getWeatherUseCase(city)
                .onSuccess { weather ->
                    _state.value = WeatherState.Success(weather, isCached = false)
                }
                .onFailure { error ->
                    _state.value = WeatherState.Error(
                        error.message ?: "An unknown error occurred"
                    )
                }
        }
    }
    
    fun refreshWeather() {
        val currentState = _state.value
        if (currentState is WeatherState.Success) {
            fetchWeather(currentState.weather.cityName, forceRefresh = true)
        }
    }
}
