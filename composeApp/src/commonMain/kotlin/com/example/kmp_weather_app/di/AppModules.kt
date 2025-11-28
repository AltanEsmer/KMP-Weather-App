package com.example.kmp_weather_app.di

import com.example.kmp_weather_app.data.network.networkModule
import com.example.kmp_weather_app.data.repository.repositoryModule
import com.example.kmp_weather_app.domain.usecase.useCaseModule
import com.example.kmp_weather_app.presentation.weather.presentationModule

fun appModules() = listOf(
    networkModule,
    repositoryModule,
    useCaseModule,
    presentationModule
)
