package com.kaizendeveloper.testweatherapp.core.network

import com.kaizendeveloper.testweatherapp.core.failure.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityInterceptor @Inject constructor(
    private val connectivityChecker: ConnectivityChecker
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!connectivityChecker.isNetworkAvailable()) {
            throw NoConnectivityException()
        } else {
            chain.proceed(chain.request())
        }
    }
}