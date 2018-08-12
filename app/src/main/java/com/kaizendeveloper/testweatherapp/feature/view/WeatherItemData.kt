package com.kaizendeveloper.testweatherapp.feature.view

import com.kaizendeveloper.testweatherapp.core.common.FeedFormatter
import com.kaizendeveloper.testweatherapp.core.db.WeatherEntity

data class WeatherItemData(
    val latLong: String,
    val timezone: String,
    val temperature: String,
    val currentlySummary: String,
    val windSpeed: String,
    val humidity: String,
    val minutelySummary: String?,
    val hourlySummary: String
) {

    companion object {

        fun fromEntity(entity: WeatherEntity, formatter: FeedFormatter): WeatherItemData {
            return WeatherItemData(
                latLong = formatter.concatCoordinates(entity.latitude, entity.longitude),
                timezone = entity.timezone,
                temperature = formatter.formatTemperature(entity.temperature, entity.units),
                currentlySummary = entity.currentlySummary,
                windSpeed = entity.windSpeed.toString(),
                humidity = entity.humidity.toString(),
                minutelySummary = entity.minutelySummary,
                hourlySummary = entity.hourlySummary
            )
        }
    }
}