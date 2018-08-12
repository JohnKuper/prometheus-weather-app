package com.kaizendeveloper.testweatherapp

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.kaizendeveloper.testweatherapp.core.db.WeatherDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class DbTest {

    @get:Rule
    val trampolineSchedulerRule = TrampolineSchedulerRule()

    lateinit var db: WeatherDatabase

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            WeatherDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() {
        db.close()
    }
}