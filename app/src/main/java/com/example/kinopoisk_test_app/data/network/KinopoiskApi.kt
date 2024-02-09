package com.example.kinopoisk_test_app.data.network

import com.example.kinopoisk_test_app.data.dto.responses.MoviesResponseDto
import retrofit2.http.GET

interface KinopoiskApi {

    suspend fun getAllMovies(): MoviesResponseDto
}