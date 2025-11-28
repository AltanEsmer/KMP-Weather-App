package com.example.kmp_weather_app.data.repository

import org.koin.dsl.module

val repositoryModule = module {
    single { WeatherRepository(get(), get()) }
}
