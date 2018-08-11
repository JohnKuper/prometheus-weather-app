package com.kaizendeveloper.testweatherapp

import android.app.Application
import com.kaizendeveloper.testweatherapp.core.di.ApplicationComponent
import com.kaizendeveloper.testweatherapp.core.di.DaggerApplicationComponent

class WeatherApplication : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
    }
}