package com.kaizendeveloper.testweatherapp.feature.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    @GET("/{API_KEY}/{location}")
    fun getWeather(
        @Path("API_KEY") apiKey: String,
        @Path("location") latLong: String,
        @Query("lang") language: String,
        @Query("units") units: String
    ): Single<WeatherData>
}