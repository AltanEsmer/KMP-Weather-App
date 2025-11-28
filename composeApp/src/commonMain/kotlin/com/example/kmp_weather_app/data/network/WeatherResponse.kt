package com.example.kmp_weather_app.data.network

import com.example.kmp_weather_app.domain.model.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("name")
    val name: String,
    @SerialName("main")
    val main: Main,
    @SerialName("weather")
    val weather: List<WeatherInfo>,
    @SerialName("wind")
    val wind: Wind
)

@Serializable
data class Main(
    @SerialName("temp")
    val temp: Double,
    @SerialName("humidity")
    val humidity: Int
)

@Serializable
data class WeatherInfo(
    @SerialName("description")
    val description: String
)

@Serializable
data class Wind(
    @SerialName("speed")
    val speed: Double
)

fun WeatherResponse.toDomain(): Weather {
    return Weather(
        cityName = name,
        temperature = main.temp,
        description = weather.firstOrNull()?.description ?: "",
        humidity = main.humidity,
        windSpeed = wind.speed
    )
}
