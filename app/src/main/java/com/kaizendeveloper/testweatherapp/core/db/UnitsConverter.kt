package com.kaizendeveloper.testweatherapp.core.db

import android.arch.persistence.room.TypeConverter
import com.kaizendeveloper.testweatherapp.feature.api.FeedUnits

class UnitsConverter {
    @TypeConverter
    fun fromCode(code: String): FeedUnits = FeedUnits.fromCode(code)

    @TypeConverter
    fun toCode(units: FeedUnits): String = units.code
}