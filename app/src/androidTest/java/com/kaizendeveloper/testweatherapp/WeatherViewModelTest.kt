package com.kaizendeveloper.testweatherapp

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.google.android.gms.maps.model.LatLng
import com.kaizendeveloper.testweatherapp.core.common.FeedFormatter
import com.kaizendeveloper.testweatherapp.core.common.FeedPreferencesHelper
import com.kaizendeveloper.testweatherapp.core.extensions.requireValue
import com.kaizendeveloper.testweatherapp.feature.api.FeedUnits
import com.kaizendeveloper.testweatherapp.feature.api.NetworkWeatherRepository
import com.kaizendeveloper.testweatherapp.feature.api.WeatherApi
import com.kaizendeveloper.testweatherapp.feature.view.WeatherFeedViewModel
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
class WeatherViewModelTest : DbTest() {

    private val mockLocation = LatLng(51.500334, -0.085013)
    private var mockWebServer: MockWebServer = MockWebServer()

    private lateinit var service: WeatherApi
    private lateinit var weatherFeedViewModel: WeatherFeedViewModel
    private lateinit var preferencesHelper: FeedPreferencesHelper
    private lateinit var feedFormatter: FeedFormatter

    @Before
    fun setup() {
        preferencesHelper = FeedPreferencesHelper(InstrumentationRegistry.getContext())
        feedFormatter = FeedFormatter()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(WeatherApi::class.java)

        val repo = NetworkWeatherRepository(service, db.weatherDao())
        weatherFeedViewModel = WeatherFeedViewModel(repo, preferencesHelper, feedFormatter)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun locationShouldBeAddedAndLiveDataShouldBeUpdated() {
        enqueueResponse("weather.json")
        weatherFeedViewModel.addLocation(mockLocation)

        val dbFeed = db.weatherDao().getAll().requireValue()
        assertEquals(1, dbFeed.size)

        val entity = dbFeed[0]
        assertEquals(51.500334, entity.latitude)
        assertEquals(-0.085013, entity.longitude)
        assertEquals("Europe/London", entity.timezone)
        assertEquals(18.72f, entity.temperature)
        assertEquals("Clear", entity.currentlySummary)
        assertEquals(2.69f, entity.windSpeed)
        assertEquals(0.48f, entity.humidity)
        assertEquals("Clear for the hour.", entity.minutelySummary)
        assertEquals("Light rain later this evening.", entity.hourlySummary)
        assertEquals(FeedUnits.SI, entity.units)

        val liveDataFeed = weatherFeedViewModel.weatherFeed.requireValue()
        assertEquals(1, liveDataFeed.size)
    }

    private fun enqueueResponse(fileName: String) {
        val inputStream = InstrumentationRegistry.getContext().assets.open(fileName)
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        mockWebServer.enqueue(
            mockResponse.setBody(source.readString(Charsets.UTF_8))
        )
    }
}