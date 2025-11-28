package com.example.kmp_weather_app.data.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class WeatherApi(private val client: HttpClient) {
    
    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5"
        private const val API_KEY = "68e7fe8106dee950e737b798fba7315b" 
        
        // JSON parser configured to ignore unknown keys (matches NetworkModule config)
        private val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
    
    suspend fun getWeatherByCity(city: String): WeatherResponse {
        val response = client.get("$BASE_URL/weather") {
            parameter("q", city)
            parameter("appid", API_KEY)
            parameter("units", "metric")
        }
        
        // OpenWeatherMap returns 200 even for errors, so we need to check the response body
        val responseText = response.bodyAsText()
        
        // Debug: Log the response (remove in production)
        println("API Response: $responseText")
        
        val jsonObject = json.parseToJsonElement(responseText).jsonObject
        
        // Check if response contains error code (OpenWeatherMap error responses have "cod" field)
        val errorCode = jsonObject["cod"]?.jsonPrimitive?.content?.toIntOrNull()
        if (errorCode != null && errorCode != 200) {
            val errorMessage = jsonObject["message"]?.jsonPrimitive?.content?.trim('"') 
                ?: "Unknown error (Code: $errorCode)"
            throw Exception("API Error: $errorMessage")
        }
        
        // If no error code, try to deserialize as WeatherResponse
        return json.decodeFromString<WeatherResponse>(responseText)
    }
}
