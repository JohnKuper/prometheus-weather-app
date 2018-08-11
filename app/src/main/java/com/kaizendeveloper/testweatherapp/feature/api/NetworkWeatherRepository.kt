package com.kaizendeveloper.testweatherapp.feature.api

import android.arch.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.kaizendeveloper.testweatherapp.core.db.WeatherDao
import com.kaizendeveloper.testweatherapp.core.db.WeatherEntity
import com.kaizendeveloper.testweatherapp.core.failure.Failure
import com.kaizendeveloper.testweatherapp.core.network.RepositoryResponse
import io.reactivex.Single
import javax.inject.Inject

class NetworkWeatherRepository @Inject constructor(
    private val weatherService: WeatherApi,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    val weatherEntities: LiveData<List<WeatherEntity>> = weatherDao.getAll()

    //TODO add correct error handling
    override fun addLocation(location: LatLng): Single<RepositoryResponse<WeatherData>> {
        return weatherService.getWeather(
            "c6987ba1fcc7450aea9cff041bb42825",
            "${location.latitude},${location.longitude}"
        )
            .doOnSuccess { weatherDao.insert(WeatherEntity.fromData(it)) }
            .flatMap {
                Single.just(RepositoryResponse.success(it))
            }.onErrorResumeNext {
                Single.just(RepositoryResponse.failure(Failure.ServerError()))
            }
    }
}