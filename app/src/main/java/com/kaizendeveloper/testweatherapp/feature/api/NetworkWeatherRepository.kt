package com.kaizendeveloper.testweatherapp.feature.api

import android.arch.lifecycle.LiveData
import android.database.sqlite.SQLiteConstraintException
import com.google.android.gms.maps.model.LatLng
import com.kaizendeveloper.testweatherapp.BuildConfig
import com.kaizendeveloper.testweatherapp.core.db.WeatherDao
import com.kaizendeveloper.testweatherapp.core.db.WeatherEntity
import com.kaizendeveloper.testweatherapp.core.failure.Failure
import com.kaizendeveloper.testweatherapp.core.failure.NoConnectivityException
import com.kaizendeveloper.testweatherapp.core.network.RepositoryResponse
import io.reactivex.Single
import javax.inject.Inject

class NetworkWeatherRepository @Inject constructor(
    private val weatherService: WeatherApi,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    val weatherEntities: LiveData<List<WeatherEntity>> = weatherDao.getAll()

    override fun addLocation(
        location: LatLng,
        lang: String,
        units: String
    ): Single<RepositoryResponse<WeatherData>> {
        return processWeather(location, lang, units) { weatherDao.insert(WeatherEntity.fromData(it)) }
    }

    override fun updateLocation(
        location: LatLng,
        lang: String,
        units: String
    ): Single<RepositoryResponse<WeatherData>> {
        return processWeather(location, lang, units) { weatherDao.update(WeatherEntity.fromData(it)) }
    }

    private fun processWeather(
        location: LatLng,
        lang: String,
        units: String,
        onSuccess: (WeatherData) -> Unit
    ): Single<RepositoryResponse<WeatherData>> {
        return weatherService.getWeather(BuildConfig.API_KEY, location.concatCoordinates(), lang, units)
            .doOnSuccess { onSuccess(it) }
            .flatMap {
                Single.just(RepositoryResponse.success(it))
            }.onErrorReturn {
                handleError(it)
            }
    }

    //TODO extract to ErrorHandler
    private fun handleError(throwable: Throwable): RepositoryResponse<WeatherData> {
        val failure = when (throwable) {
            is NoConnectivityException -> Failure.NetworkConnection()
            is SQLiteConstraintException -> Failure.DuplicatedLocation()
            else -> Failure.ServerError()
        }
        return RepositoryResponse.failure(failure)
    }

    private fun LatLng.concatCoordinates() = "$latitude,$longitude"
}