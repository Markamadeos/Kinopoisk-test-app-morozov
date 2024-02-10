package com.example.kinopoisk_test_app.data.network.api

import com.example.kinopoisk_test_app.data.network.dto.responses.MovieResponseDto
import com.example.kinopoisk_test_app.data.network.dto.responses.MoviesResponseDto
import com.example.kinopoisk_test_app.util.API_TOKEN
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {

    @Headers("x-api-key: $API_TOKEN")
    @GET("/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun getAllMovies(): MoviesResponseDto

    @Headers("x-api-key: $API_TOKEN")
    @GET("/api/v2.2/films/top/{id}")
    suspend fun getMovieById(@Path("id") movieId: String): MovieResponseDto


    @Headers("x-api-key: $API_TOKEN")
    @GET("/api/v2.2/films/top")
    suspend fun searchMovies(@Query("keyword") query: String): MoviesResponseDto
}