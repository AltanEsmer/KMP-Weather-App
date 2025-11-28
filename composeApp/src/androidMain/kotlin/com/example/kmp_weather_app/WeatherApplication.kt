package com.example.kmp_weather_app

import android.app.Application
import com.example.kmp_weather_app.data.local.localStorageModule
import com.example.kmp_weather_app.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WeatherApplication)
            modules(appModules() + localStorageModule)
        }
    }
}
