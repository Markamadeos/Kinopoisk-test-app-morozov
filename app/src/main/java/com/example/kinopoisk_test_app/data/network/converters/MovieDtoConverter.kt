package com.example.kinopoisk_test_app.data.network.converters

import com.example.kinopoisk_test_app.data.network.dto.responses.CountryDto
import com.example.kinopoisk_test_app.data.network.dto.responses.GenreDto
import com.example.kinopoisk_test_app.data.network.dto.responses.MovieResponseDto
import com.example.kinopoisk_test_app.domian.models.Movie

class MovieDtoConverter {

    fun map(movieDto: MovieResponseDto): Movie {
        return Movie(
            id = movieDto.id,
            name = movieDto.name ?: EMPTY_PARAM,
            cover = movieDto.cover ?: EMPTY_PARAM,
            coverSmall = movieDto.coverSmall ?: EMPTY_PARAM,
            year = movieDto.year.toString(),
            description = movieDto.description ?: EMPTY_PARAM,
            countries = getCountries(movieDto.countries),
            genres = getGenres(movieDto.genres)
        )
    }

    private fun getCountries(countries: List<CountryDto>?): String {
        if (countries == null) {
            return EMPTY_PARAM
        }
        val countriesString = StringBuilder("")
        countries.forEach { countriesString.append("+$it.countryName, ") }
        return countriesString.toString()
    }

    private fun getGenres(genres: List<GenreDto>?): String {
        if (genres == null) {
            return EMPTY_PARAM
        }
        val genresString = StringBuilder("")
        genres.forEach { genresString.append("+${it.genre}, ") }
        return genresString.toString()
    }

    companion object {
        private const val EMPTY_PARAM = ""
    }
}