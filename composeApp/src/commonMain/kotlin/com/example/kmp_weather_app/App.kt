package com.example.kmp_weather_app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.example.kmp_weather_app.presentation.weather.WeatherViewModel
import com.example.kmp_weather_app.presentation.weather.WeatherState
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            WeatherScreen()
        }
    }
}

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = koinInject()
) {
    val state by viewModel.state.collectAsState()
    var cityInput by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "KMP Weather App - Phase 1",
            style = MaterialTheme.typography.headlineMedium
        )
        
        OutlinedTextField(
            value = cityInput,
            onValueChange = { cityInput = it },
            label = { Text("Enter city name") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Button(
            onClick = { viewModel.fetchWeather(cityInput) },
            enabled = cityInput.isNotBlank()
        ) {
            Text("Fetch Weather")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        when (val currentState = state) {
            is WeatherState.Idle -> {
                Text("Enter a city name to fetch weather")
            }
            is WeatherState.Loading -> {
                CircularProgressIndicator()
            }
            is WeatherState.Success -> {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = currentState.weather.cityName,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text("Temperature: ${currentState.weather.temperature}°C")
                        Text("Description: ${currentState.weather.description}")
                        Text("Humidity: ${currentState.weather.humidity}%")
                        Text("Wind Speed: ${currentState.weather.windSpeed} m/s")
                    }
                }
            }
            is WeatherState.Error -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = "Error: ${currentState.message}",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
    }
}