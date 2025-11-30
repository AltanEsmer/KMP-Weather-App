package com.example.kmp_weather_app

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.GlobalContext

fun MainViewController() = ComposeUIViewController { 
    if (GlobalContext.getOrNull() == null) {
        initKoin()
    }
    App() 
}