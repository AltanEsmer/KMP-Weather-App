package com.example.kmp_weather_app.domain.usecase

import org.koin.dsl.module

val useCaseModule = module {
    single { GetWeatherUseCase(get()) }
    single { GetLastWeatherUseCase(get()) }
}
