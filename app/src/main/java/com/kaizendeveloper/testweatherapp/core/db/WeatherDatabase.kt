package com.kaizendeveloper.testweatherapp.core.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(UnitsConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        const val DATABASE_NAME = "weather.db"
    }
}