package com.example.kinopoisk_test_app.presentation.models

import androidx.annotation.StringRes
import com.example.kinopoisk_test_app.domian.models.Movie

sealed interface DetailScreenState {
    object Loading : DetailScreenState
    data class Content(val movie: Movie) : DetailScreenState
    data class ServerError(@StringRes val message: Int) : DetailScreenState
    data class InternetError(@StringRes val message: Int) : DetailScreenState
}