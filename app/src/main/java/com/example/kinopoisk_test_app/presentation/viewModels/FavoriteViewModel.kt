package com.example.kinopoisk_test_app.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk_test_app.R
import com.example.kinopoisk_test_app.domian.api.FavoriteInteractor
import com.example.kinopoisk_test_app.domian.models.Movie
import com.example.kinopoisk_test_app.presentation.models.FavoriteScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteInteractor: FavoriteInteractor) : ViewModel() {
    private val _screenState: MutableLiveData<FavoriteScreenState> = MutableLiveData()
    val screenState: LiveData<FavoriteScreenState> = _screenState

    fun getMovies() {
        _screenState.postValue(FavoriteScreenState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            favoriteInteractor.getMovies().collect {
                processingResult(it)
            }
        }
    }

    private fun processingResult(movies: List<Movie>) {
        if (movies.isEmpty()) {
            _screenState.postValue(FavoriteScreenState.Empty(R.string.nothing_found_favorites))
        } else {
            _screenState.postValue(FavoriteScreenState.Content(movies))
        }
    }

    fun deleteMovieFromFavorites(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteInteractor.deleteMovieFromFavorites(movie)
        }
    }
}