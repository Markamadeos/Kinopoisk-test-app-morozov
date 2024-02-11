package com.example.kinopoisk_test_app.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk_test_app.domian.api.FavoriteInteractor
import com.example.kinopoisk_test_app.domian.api.SearchInteractor
import com.example.kinopoisk_test_app.domian.models.Movie
import com.example.kinopoisk_test_app.domian.models.SearchResultData
import com.example.kinopoisk_test_app.presentation.models.DetailScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val searchInteractor: SearchInteractor,
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel() {
    private val _screenState: MutableLiveData<DetailScreenState> = MutableLiveData()
    val screenState: LiveData<DetailScreenState> = _screenState

    fun getDataFromNetwork(movieId: String) {
        _screenState.postValue(DetailScreenState.Loading)
        viewModelScope.launch {
            searchInteractor.getMovieById(movieId).collect { result ->
                processingResult(result)
            }
        }
    }

    fun getDataFromDb(movieId: String) {
        _screenState.postValue(DetailScreenState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            favoriteInteractor.getMovieById(movieId).collect {
                processingResult(SearchResultData.Data(it))
            }
        }
    }

    private fun processingResult(result: SearchResultData<Movie>) {
        when (result) {
            is SearchResultData.Empty -> {
                _screenState.postValue(DetailScreenState.ServerError(result.message))
            }

            is SearchResultData.Data -> {
                if (result.value != null) {
                    _screenState.postValue(DetailScreenState.Content(result.value))
                } else {
                    _screenState.postValue(DetailScreenState.Loading)
                }
            }

            is SearchResultData.NoInternet -> {
                _screenState.postValue(DetailScreenState.InternetError(result.message))
            }

            is SearchResultData.ServerError -> {
                _screenState.postValue(DetailScreenState.ServerError(result.message))
            }
        }
    }

}