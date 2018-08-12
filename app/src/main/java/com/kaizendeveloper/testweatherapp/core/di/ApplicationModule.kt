package com.kaizendeveloper.testweatherapp.core.di

import android.app.Application
import android.content.Context
import com.kaizendeveloper.testweatherapp.core.common.FeedFormatter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Suppress("unused")
@Module(
    includes = [
        NetworkModule::class,
        ViewModelsModule::class,
        DatabaseModule::class
    ]
)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideUnitsFormatter() = FeedFormatter()
}