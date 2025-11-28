package com.example.kmp_weather_app.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kmp_weather_app.domain.model.Weather

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    @ColumnInfo(name = "city_name")
    val cityName: String,
    
    @ColumnInfo(name = "temperature")
    val temperature: Double,
    
    @ColumnInfo(name = "description")
    val description: String,
    
    @ColumnInfo(name = "humidity")
    val humidity: Int,
    
    @ColumnInfo(name = "wind_speed")
    val windSpeed: Double,
    
    @ColumnInfo(name = "timestamp")
    val timestamp: Long
)

fun Weather.toEntity(): WeatherEntity {
    return WeatherEntity(
        cityName = cityName,
        temperature = temperature,
        description = description,
        humidity = humidity,
        windSpeed = windSpeed,
        timestamp = System.currentTimeMillis()
    )
}

fun WeatherEntity.toDomain(): Weather {
    return Weather(
        cityName = cityName,
        temperature = temperature,
        description = description,
        humidity = humidity,
        windSpeed = windSpeed
    )
}
