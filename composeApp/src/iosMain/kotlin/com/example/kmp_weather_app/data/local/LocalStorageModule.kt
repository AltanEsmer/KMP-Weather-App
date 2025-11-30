package com.example.kmp_weather_app.data.local

import org.koin.dsl.module

val iosLocalStorageModule = module {
    single<LocalWeatherDataSource> {
        createLocalWeatherDataSource()
    }
}
