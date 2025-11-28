package com.example.kmp_weather_app.presentation.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kmp_weather_app.presentation.weather.components.*
import org.koin.compose.koinInject

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = koinInject()
) {
    val state by viewModel.state.collectAsState()
    var cityInput by remember { mutableStateOf("") }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 600.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // App title
            Text(
                text = "KMP Weather App",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            
            // Search bar
            SearchBar(
                city = cityInput,
                onCityChange = { cityInput = it },
                onSearch = { viewModel.fetchWeather(cityInput) },
                isLoading = state is WeatherState.Loading,
                enabled = true
            )
            
            // State-based content
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.TopCenter
            ) {
                when (val currentState = state) {
                    is WeatherState.Idle -> {
                        IdleState()
                    }
                    
                    is WeatherState.Loading -> {
                        LoadingState(
                            message = "Fetching weather data...",
                            modifier = Modifier.padding(top = 48.dp)
                        )
                    }
                    
                    is WeatherState.Success -> {
                        WeatherCard(
                            weather = currentState.weather,
                            isCached = currentState.isCached,
                            onRefresh = { viewModel.fetchWeather(currentState.weather.cityName) }
                        )
                    }
                    
                    is WeatherState.Error -> {
                        ErrorState(
                            message = currentState.message,
                            onRetry = { 
                                if (cityInput.isNotBlank()) {
                                    viewModel.fetchWeather(cityInput)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun IdleState() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Welcome!",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Enter a city name to fetch current weather information",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
