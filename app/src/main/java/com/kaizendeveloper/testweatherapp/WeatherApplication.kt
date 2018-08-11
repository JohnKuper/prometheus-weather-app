package com.kaizendeveloper.testweatherapp

import android.app.Application
import com.kaizendeveloper.testweatherapp.core.di.ApplicationComponent
import com.kaizendeveloper.testweatherapp.core.di.DaggerApplicationComponent

class WeatherApplication : Application() {

    lateinit var appComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        application = this
        appComponent = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
    }

    companion object {
        lateinit var application: WeatherApplication
    }
}