package com.kaizendeveloper.testweatherapp.feature.view

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kaizendeveloper.testweatherapp.R

class WeatherAdapter(
    diffCallback: DiffUtil.ItemCallback<WeatherItemData>
) : ListAdapter<WeatherItemData, WeatherAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        with(holder) {
            timeZone.text = item.timezone
            temperature.text = item.temperature
            latLong.text = item.latLong
            currentlySummary.text = item.currentlySummary
            minutelySummary.text = item.minutelySummary
            windSpeed.text = item.windSpeed
            humidity.text = item.humidity
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timeZone: TextView = itemView.findViewById(R.id.time_zone)
        val temperature: TextView = itemView.findViewById(R.id.temperature)
        val latLong: TextView = itemView.findViewById(R.id.lat_lng)
        val currentlySummary: TextView = itemView.findViewById(R.id.current_summary)
        val minutelySummary: TextView = itemView.findViewById(R.id.minutely_summary)
        val windSpeed: TextView = itemView.findViewById(R.id.wind_speed)
        val humidity: TextView = itemView.findViewById(R.id.humidity)
    }
}