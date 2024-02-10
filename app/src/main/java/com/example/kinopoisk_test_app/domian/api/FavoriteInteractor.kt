package com.example.kinopoisk_test_app.domian.api

import com.example.kinopoisk_test_app.data.db.entity.MovieEntity

interface FavoriteInteractor {
    suspend fun saveMovieToDb(movie: MovieEntity)
    suspend fun getMovies(): List<MovieEntity>
    suspend fun getMovieById(movieId: String): MovieEntity?
    suspend fun deleteMovieFromFavorites(movie: MovieEntity)
    suspend fun isMovieInFavorites(movieId: String): Boolean
}