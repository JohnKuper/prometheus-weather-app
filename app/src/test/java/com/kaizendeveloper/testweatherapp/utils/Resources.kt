package com.kaizendeveloper.testweatherapp.utils

import java.util.Scanner

fun <T> fromResource(path: String, cls: Class<T>): T =
    GsonProvider.gson.fromJson(fromResourceInner(path, cls), cls)

private fun <T> fromResourceInner(path: String, cls: Class<T>): String =
    Scanner(cls.getResourceAsStream(path))
        .useDelimiter("\\A")
        .next()
