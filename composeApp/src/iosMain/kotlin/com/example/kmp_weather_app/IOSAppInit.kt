package com.example.kmp_weather_app

import com.example.kmp_weather_app.data.local.iosLocalStorageModule
import com.example.kmp_weather_app.di.appModules
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModules() + iosLocalStorageModule)
    }
}
