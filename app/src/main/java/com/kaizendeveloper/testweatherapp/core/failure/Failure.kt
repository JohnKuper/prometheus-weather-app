package com.kaizendeveloper.testweatherapp.core.failure

sealed class Failure {
    class NetworkConnection : Failure()
    class ServerError : Failure()
}