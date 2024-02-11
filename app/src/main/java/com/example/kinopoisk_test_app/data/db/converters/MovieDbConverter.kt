package com.example.kinopoisk_test_app.data.db.converters

import com.example.kinopoisk_test_app.data.db.entity.MovieEntity
import com.example.kinopoisk_test_app.domian.models.Movie

class MovieDbConverter {
    fun map(movie: Movie): MovieEntity {
        return MovieEntity(
            id = movie.id,
            name = movie.name,
            cover = movie.cover,
            coverSmall = movie.coverSmall,
            year = movie.year,
            description = movie.description,
            countries = movie.countries,
            genres = movie.genres
        )
    }

    fun map(movie: MovieEntity): Movie {
        return Movie(
            id = movie.id,
            name = movie.name,
            cover = movie.cover,
            coverSmall = movie.coverSmall,
            year = movie.year,
            description = movie.description,
            countries = movie.countries,
            genres = movie.genres,
            isFavorite = true
        )
    }
}