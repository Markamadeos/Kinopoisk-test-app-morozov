package com.example.kinopoisk_test_app.domian.api

import com.example.kinopoisk_test_app.domian.models.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun saveMovieToDb(movie: Movie)
    suspend fun getMovies(): Flow<List<Movie>>
    suspend fun getMovieById(movieId: String): Flow<Movie>
    suspend fun deleteMovieFromFavorites(movie: Movie)
    suspend fun isMovieInFavorites(movieId: String): Boolean

    suspend fun getFavoriteIds(): List<String>
}