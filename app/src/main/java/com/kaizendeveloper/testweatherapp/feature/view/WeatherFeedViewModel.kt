package com.kaizendeveloper.testweatherapp.feature.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.kaizendeveloper.testweatherapp.core.extensions.requireValue
import com.kaizendeveloper.testweatherapp.feature.api.NetworkWeatherRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherFeedViewModel @Inject constructor(
    //TODO Change to WeatherRepository
    private val weatherRepository: NetworkWeatherRepository
) : ViewModel() {

    val weatherFeed: LiveData<List<WeatherItemData>> =
        Transformations.map(weatherRepository.weatherEntities) { entities ->
            entities.map { WeatherItemData.fromEntity(it) }
        }

    val inProgress: LiveData<Boolean> = MutableLiveData()

    fun addLocation(location: LatLng) {
        weatherRepository
            .addLocation(location)
            .doOnSubscribe { setProgress(true) }
            .doAfterTerminate { setProgress(false) }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun updateFeed() {
        Observable.fromIterable(weatherRepository.weatherEntities.requireValue())
            .doOnSubscribe { setProgress(true) }
            .doAfterTerminate { setProgress(false) }
            .flatMapSingle {
                weatherRepository.addLocation(LatLng(it.latitude, it.longitude))
            }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    private fun setProgress(progress: Boolean) {
        (inProgress as MutableLiveData).postValue(progress)
    }
}