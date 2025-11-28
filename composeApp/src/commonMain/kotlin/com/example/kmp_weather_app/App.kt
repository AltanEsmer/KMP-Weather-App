package com.example.kmp_weather_app

import androidx.compose.material3.*
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.example.kmp_weather_app.presentation.weather.WeatherScreen
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            WeatherScreen()
        }
    }
}