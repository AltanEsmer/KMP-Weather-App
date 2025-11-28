package com.example.kmp_weather_app.data.repository

import com.example.kmp_weather_app.data.local.createLocalWeatherDataSource
import org.koin.dsl.module

val repositoryModule = module {
    single { createLocalWeatherDataSource() }
    single { WeatherRepository(get(), get()) }
}
