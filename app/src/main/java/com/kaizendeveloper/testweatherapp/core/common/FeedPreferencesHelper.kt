package com.kaizendeveloper.testweatherapp.core.common

import android.content.Context
import android.content.SharedPreferences
import com.kaizendeveloper.testweatherapp.feature.api.FeedLanguage
import com.kaizendeveloper.testweatherapp.feature.api.FeedUnits
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedPreferencesHelper @Inject constructor(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE)

    private fun putString(key: String, value: String) {
        prefs.edit()
            .putString(key, value)
            .apply()
    }

    fun setLanguage(language: FeedLanguage) {
        putString(KEY_LANGUAGE, language.code)
    }

    fun setUnits(units: FeedUnits) {
        putString(KEY_UNITS, units.code)
    }

    fun getLanguage(): String {
        return prefs.getString(KEY_LANGUAGE, FeedLanguage.EN.code)
    }

    fun getUnits(): String {
        return prefs.getString(KEY_UNITS, FeedUnits.AUTO.code)
    }

    companion object {
        private const val SETTINGS_NAME = "feed_settings"
        private const val KEY_LANGUAGE = "feed_language"
        private const val KEY_UNITS = "feed_units"
    }
}