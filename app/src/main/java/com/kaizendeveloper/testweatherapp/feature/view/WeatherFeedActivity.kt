package com.kaizendeveloper.testweatherapp.feature.view

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.location.places.ui.PlacePicker
import com.kaizendeveloper.testweatherapp.R
import com.kaizendeveloper.testweatherapp.WeatherApplication
import com.kaizendeveloper.testweatherapp.core.common.FeedPreferencesHelper
import com.kaizendeveloper.testweatherapp.core.extensions.observe
import com.kaizendeveloper.testweatherapp.core.extensions.viewModel
import com.kaizendeveloper.testweatherapp.core.failure.Failure
import com.kaizendeveloper.testweatherapp.feature.api.FeedLanguage
import com.kaizendeveloper.testweatherapp.feature.api.FeedUnits
import kotlinx.android.synthetic.main.activity_weather_feed.feed
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_weather_feed.add_location as addLocation
import kotlinx.android.synthetic.main.activity_weather_feed.refresh_layout as refreshLayout

const val PLACE_PICKER_REQUEST = 1

class WeatherFeedActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var preferencesHelper: FeedPreferencesHelper
    private lateinit var weatherFeedViewModel: WeatherFeedViewModel

    private val weatherAdapter = WeatherAdapter(WeatherItemDiffCallback())

    private var isMenuOpenedFirstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WeatherApplication.application.appComponent.inject(this)
        setContentView(R.layout.activity_weather_feed)

        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        feed.apply {
            adapter = weatherAdapter
            layoutManager = LinearLayoutManager(context)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    if (dy > 0 || dy < 0 && addLocation.isShown)
                        addLocation.hide()
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        addLocation.show()
                    }
                }
            })
        }

        addLocation.setOnClickListener {
            val builder = PlacePicker.IntentBuilder()
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)
        }

        refreshLayout.setOnRefreshListener {
            weatherFeedViewModel.updateFeed()
        }
    }

    private fun observeViewModel() {
        weatherFeedViewModel = viewModel(viewModelFactory) {
            observe(weatherFeed, ::populateFeed)
            observe(inProgress) { updateProgress(it ?: false) }
            observe(failure, ::handleFailure)
        }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.weather_feed_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        when (item.itemId) {
            R.id.menu_english -> setLanguage(FeedLanguage.EN)
            R.id.menu_french -> setLanguage(FeedLanguage.FR)
            R.id.menu_spanish -> setLanguage(FeedLanguage.ES)
            R.id.menu_german -> setLanguage(FeedLanguage.DE)
            R.id.menu_units_auto -> setUnits(FeedUnits.AUTO)
            R.id.menu_units_si -> setUnits(FeedUnits.SI)
            R.id.menu_units_us -> setUnits(FeedUnits.US)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        if (isMenuOpenedFirstTime) {
            setCheckedInitialMenuItems(menu)
            isMenuOpenedFirstTime = false
        }
        return super.onPrepareOptionsMenu(menu)
    }

    private fun setLanguage(language: FeedLanguage) {
        weatherFeedViewModel.language = language
    }

    private fun setUnits(units: FeedUnits) {
        weatherFeedViewModel.units = units
    }

    private fun setCheckedInitialMenuItems(menu: Menu) {
        val language = FeedLanguage.fromCode(preferencesHelper.getLanguage())
        val units = FeedUnits.fromCode(preferencesHelper.getUnits())
        when (language) {
            FeedLanguage.EN -> setChecked(menu, R.id.menu_english)
            FeedLanguage.FR -> setChecked(menu, R.id.menu_french)
            FeedLanguage.ES -> setChecked(menu, R.id.menu_spanish)
            FeedLanguage.DE -> setChecked(menu, R.id.menu_german)
        }
        when (units) {
            FeedUnits.AUTO -> setChecked(menu, R.id.menu_units_auto)
            FeedUnits.SI -> setChecked(menu, R.id.menu_units_si)
            FeedUnits.US -> setChecked(menu, R.id.menu_units_us)
        }
    }

    private fun setChecked(menu: Menu, @IdRes itemId: Int) {
        menu.findItem(itemId).isChecked = true
    }

    private fun populateFeed(list: List<WeatherItemData>?) {
        weatherAdapter.submitList(list)
    }

    private fun updateProgress(inProgress: Boolean) {
        refreshLayout.isRefreshing = inProgress
    }

    //TODO extract to failure handler
    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> showFailure(R.string.no_network_connection)
            is Failure.ServerError -> showFailure(R.string.something_went_wrong)
            is Failure.DuplicatedLocation -> showFailure(R.string.duplicated_location)
        }
    }

    private fun showFailure(@StringRes message: Int) {
        Snackbar.make(refreshLayout, message, Snackbar.LENGTH_SHORT).show()
    }
}
