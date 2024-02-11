package com.example.kinopoisk_test_app.domian.impl

import com.example.kinopoisk_test_app.domian.api.FavoriteInteractor
import com.example.kinopoisk_test_app.domian.api.FavoriteRepository
import com.example.kinopoisk_test_app.domian.models.Movie
import kotlinx.coroutines.flow.Flow

class FavoriteInteractorImpl(private val favoriteRepository: FavoriteRepository) :
    FavoriteInteractor {
    override suspend fun saveMovieToDb(movie: Movie) {
        favoriteRepository.saveMovieToDb(movie)
    }

    override suspend fun getMovies(): Flow<List<Movie>> {
        return favoriteRepository.getMovies()
    }

    override suspend fun getMovieById(movieId: String): Flow<Movie> {
        return favoriteRepository.getMovieById(movieId)
    }

    override suspend fun deleteMovieFromFavorites(movie: Movie) {
        favoriteRepository.deleteMovieFromFavorites(movie)
    }

    override suspend fun isMovieInFavorites(movieId: String): Boolean {
        return favoriteRepository.isMovieInFavorites(movieId)
    }

    override suspend fun getFavoriteIds(): List<String> {
        return favoriteRepository.getFavoriteIds()
    }
}