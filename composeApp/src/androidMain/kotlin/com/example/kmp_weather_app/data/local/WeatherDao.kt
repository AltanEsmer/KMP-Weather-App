package com.example.kmp_weather_app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)
    
    @Query("SELECT * FROM weather ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastWeather(): WeatherEntity?
    
    @Query("DELETE FROM weather")
    suspend fun clearAll()
}
