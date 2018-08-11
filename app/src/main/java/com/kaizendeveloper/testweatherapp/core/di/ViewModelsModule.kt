package com.kaizendeveloper.testweatherapp.core.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kaizendeveloper.testweatherapp.feature.view.WeatherFeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherFeedViewModel::class)
    abstract fun bindWeatherViewModel(weatherFeedViewModel: WeatherFeedViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
