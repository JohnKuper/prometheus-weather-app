package com.kaizendeveloper.testweatherapp

import com.google.gson.GsonBuilder
import com.kaizendeveloper.testweatherapp.feature.api.WeatherData
import org.junit.Assert.assertNotNull
import org.junit.Test

//TODO write good test
class WeatherDataTest {

    @Test
    fun parsingShouldBeCorrect() {
        val json = "{  \n" +
                "   \"latitude\":51.500334,\n" +
                "   \"longitude\":-0.085013,\n" +
                "   \"timezone\":\"Europe/London\",\n" +
                "   \"currently\":{  \n" +
                "      \"temperature\":18.72,\n" +
                "      \"summary\":\"Clear\",\n" +
                "      \"windSpeed\":2.69,\n" +
                "      \"humidity\":0.48\n" +
                "   },\n" +
                "   \"minutely\":{  \n" +
                "      \"summary\":\"Clear for the hour.\"\n" +
                "   }\n" +
                "}"
        val weatherData = GsonBuilder().create().fromJson<WeatherData>(json, WeatherData::class.java)
        assertNotNull(weatherData)
    }
}
