package com.kaizendeveloper.testweatherapp

import com.kaizendeveloper.testweatherapp.feature.api.FeedUnits
import com.kaizendeveloper.testweatherapp.feature.api.WeatherData
import com.kaizendeveloper.testweatherapp.utils.fromResource
import junit.framework.Assert.assertEquals
import org.junit.Test

class WeatherDataTest {

    @Test
    fun testDeserializing() {
        val weatherData = fromResource("/json/weather.json", WeatherData::class.java)

        assertEquals(51.500334, weatherData.latitude)
        assertEquals(-0.085013, weatherData.longitude)
        assertEquals("Europe/London", weatherData.timezone)

        val currently = weatherData.currently
        assertEquals(18.72f, currently.temperature)
        assertEquals("Clear", currently.summary)
        assertEquals(2.69f, currently.windSpeed)
        assertEquals(0.48f, currently.humidity)
        assertEquals("Clear for the hour.", weatherData.minutely?.summary)
        assertEquals("Light rain later this evening.", weatherData.hourly.summary)
        assertEquals(FeedUnits.SI, weatherData.flags.units)
    }
}
