package com.example.kinopoisk_test_app.data.db.impl

import com.example.kinopoisk_test_app.data.db.AppDataBase
import com.example.kinopoisk_test_app.data.db.converters.MovieDbConverter
import com.example.kinopoisk_test_app.data.db.entity.MovieEntity
import com.example.kinopoisk_test_app.domian.api.FavoriteRepository
import com.example.kinopoisk_test_app.domian.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteRepositoryImpl(
    private val appDataBase: AppDataBase,
    private val converter: MovieDbConverter
) : FavoriteRepository {
    override suspend fun saveMovieToDb(movie: Movie) {
        appDataBase.movieDao().insertMovie(converter.map(movie))
    }

    override suspend fun getMovies(): Flow<List<Movie>> = flow {
        emit(convertMovieFromDbEntity(appDataBase.movieDao().getMovies()))
    }

    override suspend fun getMovieById(movieId: String): Movie? {
        return appDataBase.movieDao().getMovie(movieId)?.let { converter.map(it) }
    }

    override suspend fun deleteMovieFromFavorites(movie: Movie) {
        appDataBase.movieDao().deleteMovie(converter.map(movie))
    }

    override suspend fun isMovieInFavorites(movieId: String): Boolean {
        return appDataBase.movieDao().getIds().contains(movieId)
    }

    override suspend fun getFavoriteIds(): List<String> {
        return appDataBase.movieDao().getIds()
    }

    private fun convertMovieFromDbEntity(moviesEntity: List<MovieEntity>): List<Movie> {
        return moviesEntity.map { movieEntity -> converter.map(movieEntity) }
    }
}