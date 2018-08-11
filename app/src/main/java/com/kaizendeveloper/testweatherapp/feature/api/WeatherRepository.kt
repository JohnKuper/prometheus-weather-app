package com.kaizendeveloper.testweatherapp.feature.api

import com.google.android.gms.maps.model.LatLng
import com.kaizendeveloper.testweatherapp.core.network.RepositoryResponse
import io.reactivex.Single

interface WeatherRepository {

    fun getWeather(location: LatLng): Single<RepositoryResponse<WeatherData>>
}