package com.kaizendeveloper.testweatherapp.core.common

import com.kaizendeveloper.testweatherapp.feature.api.FeedUnits
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class FeedFormatter {

    fun formatTemperature(temp: Float, units: FeedUnits): String {
        return when (units) {
            FeedUnits.AUTO -> throw UnsupportedOperationException()
            FeedUnits.SI -> "$temp$APOSTROPHE$CELSIUS"
            FeedUnits.US -> "$temp$APOSTROPHE$FAHRENHEIT"
        }
    }

    fun concatCoordinates(lat: Double, long: Double): String {
        val latFormatted = decimalFormat.format(lat)
        val longFormatted = decimalFormat.format(long)
        return "$latFormatted, $longFormatted"
    }

    companion object {

        private val decimalFormat = DecimalFormat("0.000000", DecimalFormatSymbols(Locale.UK))

        private const val CELSIUS = 'C'
        private const val FAHRENHEIT = 'F'
        private const val APOSTROPHE = "'"
    }
}