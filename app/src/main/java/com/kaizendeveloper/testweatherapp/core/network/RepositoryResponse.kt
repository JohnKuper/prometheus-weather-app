package com.kaizendeveloper.testweatherapp.core.network

import com.kaizendeveloper.testweatherapp.core.failure.Failure

class RepositoryResponse<out T> private constructor(
    private val _error: Failure? = null,
    private val _result: T? = null
) {

    val isSuccessful: Boolean = _error == null && _result != null

    fun handle(onError: (Failure) -> Unit = {}, onResult: (T) -> Unit = {}) {
        if (isSuccessful) {
            onResult(_result!!)
        } else {
            onError(_error!!)
        }
    }

    companion object {
        fun <T> success(result: T): RepositoryResponse<T> =
            RepositoryResponse(_result = result)

        fun <T> failure(error: Failure): RepositoryResponse<T> =
            RepositoryResponse(error)
    }
}
