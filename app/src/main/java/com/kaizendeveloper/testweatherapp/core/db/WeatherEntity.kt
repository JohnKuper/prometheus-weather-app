package com.kaizendeveloper.testweatherapp.core.db

import android.arch.persistence.room.Entity
import com.kaizendeveloper.testweatherapp.feature.api.FeedUnits
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
    val minutelySummary: String?,
    val units: FeedUnits
) {

    companion object {

        fun fromData(weatherData: WeatherData): WeatherEntity {
            val currently = weatherData.currently
            return WeatherEntity(
                latitude = weatherData.latitude,
                longitude = weatherData.longitude,
                timezone = weatherData.timezone,
                temperature = currently.temperature,
                currentlySummary = currently.summary,
                windSpeed = currently.windSpeed,
                humidity = currently.humidity,
                minutelySummary = weatherData.minutely?.summary,
                units = weatherData.flags.units
            )
        }
    }
}
