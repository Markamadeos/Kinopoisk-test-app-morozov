package com.example.kinopoisk_test_app.domian.impl

import com.example.kinopoisk_test_app.domian.api.SearchInteractor
import com.example.kinopoisk_test_app.domian.api.SearchRepository
import com.example.kinopoisk_test_app.domian.models.Movie
import com.example.kinopoisk_test_app.domian.models.SearchResultData
import kotlinx.coroutines.flow.Flow

class SearchInteractorImpl(private val searchRepository: SearchRepository) : SearchInteractor {
    override suspend fun getPopularMovies(): Flow<SearchResultData<List<Movie>>> {
        return searchRepository.getPopularMovies()
    }

    override suspend fun getMovieById(movieId: String): Flow<SearchResultData<Movie>> {
        return searchRepository.getMovieById(movieId)
    }

    override suspend fun searchMovies(query: String): Flow<SearchResultData<List<Movie>>> {
        return searchRepository.searchMovies(query)
    }
}