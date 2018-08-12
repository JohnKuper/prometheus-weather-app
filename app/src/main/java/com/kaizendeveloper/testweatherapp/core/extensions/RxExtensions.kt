package com.kaizendeveloper.testweatherapp.core.extensions

import io.reactivex.Observable
import io.reactivex.Single

fun <T> Observable<T>.notifyProgress(callback: (Boolean) -> Unit): Observable<T> {
    return doOnSubscribe { callback(true) }
        .doAfterTerminate { callback(false) }
        .doOnDispose { callback(false) }
}

fun <T> Single<T>.notifyProgress(callback: (Boolean) -> Unit): Single<T> {
    return doOnSubscribe { callback(true) }
        .doAfterTerminate { callback(false) }
        .doOnDispose { callback(false) }
}
