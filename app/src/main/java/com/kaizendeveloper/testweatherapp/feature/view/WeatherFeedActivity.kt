package com.kaizendeveloper.testweatherapp.feature.view

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.google.android.gms.location.places.ui.PlacePicker
import com.kaizendeveloper.testweatherapp.R
import com.kaizendeveloper.testweatherapp.WeatherApplication
import com.kaizendeveloper.testweatherapp.core.extensions.observe
import com.kaizendeveloper.testweatherapp.core.extensions.viewModel
import kotlinx.android.synthetic.main.activity_weather_feed.feed
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_weather_feed.add_location as addLocation
import kotlinx.android.synthetic.main.activity_weather_feed.refresh_layout as refreshLayout

const val PLACE_PICKER_REQUEST = 1

class WeatherFeedActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var weatherFeedViewModel: WeatherFeedViewModel

    private val weatherAdapter = WeatherAdapter(WeatherItemDiffCallback())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WeatherApplication.application.appComponent.inject(this)
        setContentView(R.layout.activity_weather_feed)

        setupViews()
        observeViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(this, data)
                weatherFeedViewModel.addLocation(place.latLng)
            }
        }
    }

    private fun observeViewModel() {
        weatherFeedViewModel = viewModel(viewModelFactory) {
            observe(weatherFeed, ::populateFeed)
            observe(inProgress) { updateProgress(it ?: false) }
        }
    }

    private fun setupViews() {
        feed.apply {
            adapter = weatherAdapter
            layoutManager = LinearLayoutManager(context)
        }

        addLocation.setOnClickListener {
            val builder = PlacePicker.IntentBuilder()
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)
        }

        refreshLayout.setOnRefreshListener {
            weatherFeedViewModel.updateFeed()
        }
    }

    private fun populateFeed(list: List<WeatherItemData>?) {
        weatherAdapter.submitList(list)
    }

    private fun updateProgress(inProgress: Boolean) {
        refreshLayout.isRefreshing = inProgress
    }
}
