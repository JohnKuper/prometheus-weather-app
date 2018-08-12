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
    val minutely: Minutely?,
    @SerializedName("flags")
    val flags: Flags
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

data class Flags(
    @SerializedName("units")
    val units: FeedUnits
)