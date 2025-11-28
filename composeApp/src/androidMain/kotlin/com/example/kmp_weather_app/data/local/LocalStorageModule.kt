package com.example.kmp_weather_app.data.local

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localStorageModule = module {
    
    single {
        WeatherDatabase.getInstance(androidContext())
    }
    
    single {
        get<WeatherDatabase>().weatherDao()
    }
    
    single<LocalWeatherDataSource> {
        LocalWeatherDataSourceImpl(get())
    }
}
