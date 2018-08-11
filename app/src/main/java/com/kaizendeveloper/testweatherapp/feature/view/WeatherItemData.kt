package com.kaizendeveloper.testweatherapp.feature.view

import com.kaizendeveloper.testweatherapp.core.db.WeatherEntity

data class WeatherItemData(
    val latLong: String,
    val timezone: String,
    val temperature: String,
    val currentlySummary: String,
    val windSpeed: String,
    val humidity: String,
    val minutelySummary: String?
) {

    companion object {
        //TODO add converter for different units (temperature, wind speed)
        fun fromEntity(entity: WeatherEntity): WeatherItemData {
            return WeatherItemData(
                latLong = "${entity.latitude},${entity.longitude}",
                timezone = entity.timezone,
                temperature = entity.temperature.toString(),
                currentlySummary = entity.currentlySummary,
                windSpeed = entity.windSpeed.toString(),
                humidity = entity.humidity.toString(),
                minutelySummary = entity.minutelySummary
            )
        }
    }
}