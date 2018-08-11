package com.kaizendeveloper.testweatherapp.core.di

import android.app.Application
import com.kaizendeveloper.testweatherapp.feature.view.WeatherFeedActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(weatherFeedActivity: WeatherFeedActivity)
}