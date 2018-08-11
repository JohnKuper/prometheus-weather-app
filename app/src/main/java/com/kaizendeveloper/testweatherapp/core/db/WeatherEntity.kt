package com.kaizendeveloper.testweatherapp.core.db

import android.arch.persistence.room.Entity
import com.kaizendeveloper.testweatherapp.feature.api.WeatherData

@Entity(
    tableName = "locations_weather",
    primaryKeys = ["latitude", "longitude"]
)
data class WeatherEntity(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val temperature: Float,
    val currentlySummary: String,
    val windSpeed: Float,
    val humidity: Float,
    val minutelySummary: String?
) {

    companion object {
        fun fromData(weatherData: WeatherData): WeatherEntity {
            return WeatherEntity(
                latitude = weatherData.latitude,
                longitude = weatherData.longitude,
                timezone = weatherData.timezone,
                temperature = weatherData.currently.temperature,
                currentlySummary = weatherData.currently.summary,
                windSpeed = weatherData.currently.windSpeed,
                humidity = weatherData.currently.humidity,
                minutelySummary = weatherData.minutely?.summary
            )
        }
    }
}
