package com.kaizendeveloper.testweatherapp.feature.view

import android.support.v7.util.DiffUtil

class WeatherItemDiffCallback : DiffUtil.ItemCallback<WeatherItemData>() {
    override fun areItemsTheSame(oldItem: WeatherItemData?, newItem: WeatherItemData?): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WeatherItemData?, newItem: WeatherItemData?): Boolean {
        return oldItem == newItem
    }
}