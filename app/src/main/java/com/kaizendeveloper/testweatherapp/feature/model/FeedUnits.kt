package com.kaizendeveloper.testweatherapp.feature.model

enum class FeedUnits(val code: String) {
    AUTO("auto"),
    SI("si"),
    US("us");

    companion object {
        fun fromCode(code: String): FeedUnits {
            return FeedUnits.values().first { it.code == code }
        }
    }
}