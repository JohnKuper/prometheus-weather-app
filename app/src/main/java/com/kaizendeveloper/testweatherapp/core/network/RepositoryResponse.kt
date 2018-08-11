package com.kaizendeveloper.testweatherapp.core.network

import com.kaizendeveloper.testweatherapp.core.failure.Failure

class RepositoryResponse<out T> private constructor(
    private val _error: Failure? = null,
    private val _result: T? = null
) {

    val error: Failure
        get() = _error!!

    val result: T
        get() = _result!!

    val isSuccessful: Boolean = _error == null

    companion object {
        fun <T> success(result: T): RepositoryResponse<T> =
            RepositoryResponse(_result = result)

        fun <T> failure(error: Failure): RepositoryResponse<T> =
            RepositoryResponse(error)
    }
}
