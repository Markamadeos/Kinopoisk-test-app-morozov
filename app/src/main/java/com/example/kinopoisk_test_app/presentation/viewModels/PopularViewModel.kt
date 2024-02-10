package com.example.kinopoisk_test_app.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk_test_app.domian.api.SearchInteractor
import com.example.kinopoisk_test_app.domian.models.Movie
import com.example.kinopoisk_test_app.domian.models.SearchResultData
import com.example.kinopoisk_test_app.presentation.models.PopularScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PopularViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {
    private val _screenState: MutableLiveData<PopularScreenState> = MutableLiveData()
    val screenState: LiveData<PopularScreenState> = _screenState
    private var searchJob: Job? = null
    private var currentQuery = EMPTY_QUERY


    fun getPopularMovies() {
        _screenState.postValue(PopularScreenState.Loading)
        viewModelScope.launch {
            searchInteractor.getPopularMovies().collect { result ->
                processingResult(result)
            }
        }
    }

    fun searchMovies(query: String) {
        _screenState.postValue(PopularScreenState.Loading)
        viewModelScope.launch {
            searchInteractor.searchMovies(query).collect { result ->
                processingResult(result)
            }
        }
    }

    fun debounceSearch(query: String) {
        if (query != currentQuery) {
            currentQuery = query
            searchJob?.cancel()
            if (currentQuery.length > ONE_LETTER) {
                searchJob = viewModelScope.launch {
                    delay(SEARCH_DEBOUNCE_DELAY_MILLIS)
                    searchMovies(currentQuery)
                }
            }
        }
    }

    private fun processingResult(result: SearchResultData<List<Movie>>) {
        when (result) {
            is SearchResultData.NoInternet -> {
                _screenState.postValue(PopularScreenState.InternetError(result.message))
            }

            is SearchResultData.ServerError -> {
                _screenState.postValue(PopularScreenState.ServerError(result.message))
            }

            is SearchResultData.Empty -> {
                _screenState.postValue(PopularScreenState.Empty(result.message))
            }

            is SearchResultData.Data -> {
                if (result.value != null) {
                    _screenState.postValue(PopularScreenState.Content(result.value))
                }
            }
        }
    }

    companion object {
        private const val EMPTY_QUERY = ""
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
        private const val ONE_LETTER = 1
    }
}