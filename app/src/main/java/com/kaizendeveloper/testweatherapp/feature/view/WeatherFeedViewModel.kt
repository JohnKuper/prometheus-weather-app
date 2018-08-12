package com.kaizendeveloper.testweatherapp.feature.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.kaizendeveloper.testweatherapp.core.common.FeedPreferencesHelper
import com.kaizendeveloper.testweatherapp.core.extensions.requireValue
import com.kaizendeveloper.testweatherapp.core.failure.Failure
import com.kaizendeveloper.testweatherapp.feature.api.NetworkWeatherRepository
import com.kaizendeveloper.testweatherapp.feature.model.FeedLanguage
import com.kaizendeveloper.testweatherapp.feature.model.FeedUnits
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class WeatherFeedViewModel @Inject constructor(
    //TODO Change to WeatherRepository
    private val weatherRepository: NetworkWeatherRepository,
    private val preferencesHelper: FeedPreferencesHelper
) : ViewModel() {

    var language: FeedLanguage
        get() = FeedLanguage.fromCode(preferencesHelper.getLanguage())
        set(value) {
            if (language != value) {
                preferencesHelper.setLanguage(value)
                updateFeed()
            }
        }

    var units: FeedUnits
        get() = FeedUnits.fromCode(preferencesHelper.getUnits())
        set(value) {
            if (units != value) {
                preferencesHelper.setUnits(value)
                updateFeed()
            }
        }

    val weatherFeed: LiveData<List<WeatherItemData>> =
        Transformations.map(weatherRepository.weatherEntities) { entities ->
            entities.map { WeatherItemData.fromEntity(it) }
        }

    val failure: LiveData<Failure> = MutableLiveData()
    val inProgress: LiveData<Boolean> = MutableLiveData()

    fun addLocation(location: LatLng) {
        weatherRepository
            .addLocation(location, language.code, units.code)
            .doOnSubscribe { setProgress(true) }
            .doAfterTerminate { setProgress(false) }
            .subscribeOn(Schedulers.io())
            .subscribe { response -> response.handle(::handleError) }
    }

    fun updateFeed() {
        Observable.just(weatherRepository.weatherEntities.requireValue())
            .flatMapIterable { it }
            .flatMapSingle {
                val latLng = LatLng(it.latitude, it.longitude)
                weatherRepository.updateLocation(latLng, language.code, units.code)
            }
            .doOnSubscribe { setProgress(true) }
            .doAfterTerminate { setProgress(false) }
            .subscribeOn(Schedulers.io())
            .subscribe { it.handle(::handleError) }
    }

    private fun setProgress(progress: Boolean) {
        (inProgress as MutableLiveData).postValue(progress)
    }

    private fun handleError(failure: Failure) {
        (this.failure as MutableLiveData).postValue(failure)
    }
}