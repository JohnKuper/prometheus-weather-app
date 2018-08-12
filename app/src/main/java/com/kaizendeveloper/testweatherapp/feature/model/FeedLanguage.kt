package com.kaizendeveloper.testweatherapp.feature.model

enum class FeedLanguage(val code: String) {
    EN("en"),
    FR("fr"),
    ES("es"),
    DE("de");

    companion object {
        fun fromCode(code: String): FeedLanguage {
            return values().first { it.code == code }
        }
    }
}