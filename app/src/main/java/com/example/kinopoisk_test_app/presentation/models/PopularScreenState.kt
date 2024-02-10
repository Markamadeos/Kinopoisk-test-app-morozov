package com.example.kinopoisk_test_app.presentation.models

import androidx.annotation.StringRes
import com.example.kinopoisk_test_app.domian.models.Movie

sealed interface PopularScreenState {
    object Loading : PopularScreenState
    data class Content(val movies: List<Movie>) : PopularScreenState
    data class ServerError(@StringRes val message: Int) : PopularScreenState
    data class Empty(@StringRes val message: Int) : PopularScreenState
    data class InternetError(@StringRes val message: Int) : PopularScreenState
}