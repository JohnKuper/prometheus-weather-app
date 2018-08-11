package com.kaizendeveloper.testweatherapp.feature.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.kaizendeveloper.testweatherapp.feature.api.NetworkWeatherRepository
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherFeedViewModel @Inject constructor(
    //TODO Change to WeatherRepository
    private val weatherRepository: NetworkWeatherRepository
) : ViewModel() {

    val weatherFeed: LiveData<List<WeatherItemData>> = Transformations.map(weatherRepository.weatherEntities) {
        it.map { WeatherItemData.fromEntity(it) }
    }

    fun addLocation(location: LatLng) {
        weatherRepository
            .getWeather(location)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}