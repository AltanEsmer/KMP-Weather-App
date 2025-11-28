package com.example.kmp_weather_app.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmp_weather_app.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {
    
    private val _state = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val state: StateFlow<WeatherState> = _state.asStateFlow()
    
    fun fetchWeather(city: String) {
        viewModelScope.launch {
            _state.value = WeatherState.Loading
            
            getWeatherUseCase(city)
                .onSuccess { weather ->
                    _state.value = WeatherState.Success(weather)
                }
                .onFailure { error ->
                    _state.value = WeatherState.Error(
                        error.message ?: "An unknown error occurred"
                    )
                }
        }
    }
}
