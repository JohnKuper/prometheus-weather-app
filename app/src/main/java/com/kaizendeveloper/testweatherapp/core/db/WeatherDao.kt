package com.kaizendeveloper.testweatherapp.core.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface WeatherDao {

    @Insert
    fun insert(weather: WeatherEntity)

    @Update
    fun update(weather: WeatherEntity)

    @Query("SELECT * FROM locations_weather")
    fun getAll(): LiveData<List<WeatherEntity>>
}