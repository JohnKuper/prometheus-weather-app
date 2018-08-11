package com.kaizendeveloper.testweatherapp.core.failure

sealed class Failure {
    abstract val message: String

    object NetworkConnection : Failure() {
        override val message: String
            get() = "No network available, please check your connection"
    }

    object ServerError : Failure() {
        override val message: String
            get() = "Something went wrong. Try again later"
    }
}