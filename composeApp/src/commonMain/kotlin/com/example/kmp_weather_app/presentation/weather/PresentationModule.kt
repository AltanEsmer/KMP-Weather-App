package com.example.kmp_weather_app.presentation.weather

import org.koin.dsl.module

val presentationModule = module {
    factory { WeatherViewModel(get()) }
}
