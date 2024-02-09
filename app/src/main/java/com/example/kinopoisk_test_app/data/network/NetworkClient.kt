package com.example.kinopoisk_test_app.data.network

import com.example.kinopoisk_test_app.data.network.dto.requests.MovieRequest
import com.example.kinopoisk_test_app.data.network.dto.requests.MoviesSearchRequest
import com.example.kinopoisk_test_app.data.network.dto.responses.MovieResponseDto
import com.example.kinopoisk_test_app.data.network.dto.responses.MoviesResponseDto

interface NetworkClient {

    suspend fun getAllMovies(): Result<MoviesResponseDto>

    suspend fun getMovieById(request: MovieRequest): Result<MovieResponseDto>

    suspend fun searchMovies(request: MoviesSearchRequest): Result<MoviesResponseDto>
}