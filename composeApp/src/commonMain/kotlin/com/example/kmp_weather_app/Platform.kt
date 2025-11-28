package com.example.kmp_weather_app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform