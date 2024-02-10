package com.example.kinopoisk_test_app.domian.api

import com.example.kinopoisk_test_app.domian.models.Movie
import com.example.kinopoisk_test_app.domian.models.SearchResultData
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getPopularMovies(): Flow<SearchResultData<List<Movie>>>
    suspend fun getMovieById(movieId: String): Flow<SearchResultData<Movie>>
    suspend fun searchMovies(query: String): Flow<SearchResultData<List<Movie>>>
}