package com.example.kinopoisk_test_app.presentation.models

import androidx.annotation.StringRes
import com.example.kinopoisk_test_app.domian.models.Movie

sealed interface FavoriteScreenState {
    object Loading : FavoriteScreenState
    data class Content(val movies: List<Movie>) : FavoriteScreenState
    data class Empty(@StringRes val message: Int) : FavoriteScreenState
}