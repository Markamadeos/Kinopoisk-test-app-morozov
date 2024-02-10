package com.example.kinopoisk_test_app.domian.models

import androidx.annotation.StringRes

sealed interface SearchResultData<T> {
    data class Data<T>(val value: T?) : SearchResultData<T>
    data class ServerError<T>(@StringRes val message: Int) : SearchResultData<T>
    data class NoInternet<T>(@StringRes val message: Int) : SearchResultData<T>
    data class Empty<T>(@StringRes val message: Int) : SearchResultData<T>
}