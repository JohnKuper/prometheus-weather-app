package com.kaizendeveloper.testweatherapp.feature.api

import com.google.android.gms.maps.model.LatLng
import com.kaizendeveloper.testweatherapp.core.network.RepositoryResponse
import io.reactivex.Single

interface WeatherRepository {

    fun addLocation(location: LatLng, lang: String, units: String): Single<RepositoryResponse<WeatherData>>
    fun updateLocation(location: LatLng, lang: String, units: String): Single<RepositoryResponse<WeatherData>>
}