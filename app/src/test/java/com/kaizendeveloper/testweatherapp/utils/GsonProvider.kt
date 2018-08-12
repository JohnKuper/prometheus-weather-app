package com.kaizendeveloper.testweatherapp.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonProvider {
    val gson: Gson = GsonBuilder().create()
}