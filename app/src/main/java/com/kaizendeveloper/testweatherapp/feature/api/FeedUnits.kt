package com.kaizendeveloper.testweatherapp.feature.api

import com.google.gson.annotations.SerializedName

enum class FeedUnits(val code: String) {
    AUTO("auto"),
    @SerializedName("si")
    SI("si"),
    @SerializedName("us")
    US("us");

    companion object {
        fun fromCode(code: String): FeedUnits {
            return FeedUnits.values().first { it.code == code }
        }
    }
}