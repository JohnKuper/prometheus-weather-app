package com.kaizendeveloper.testweatherapp.feature.api

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("currently")
    val currently: Currently,
    @SerializedName("minutely")
    val minutely: Minutely?
)

data class Currently(
    @SerializedName("temperature")
    val temperature: Float,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("windSpeed")
    val windSpeed: Float,
    @SerializedName("humidity")
    val humidity: Float
)

data class Minutely(
    @SerializedName("summary")
    val summary: String
)