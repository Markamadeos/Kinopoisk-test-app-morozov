package com.example.kinopoisk_test_app.data.network.impl

import android.content.Context
import com.example.kinopoisk_test_app.data.NetworkClient
import com.example.kinopoisk_test_app.data.dto.requests.MovieRequest
import com.example.kinopoisk_test_app.data.dto.requests.MoviesSearchRequest
import com.example.kinopoisk_test_app.data.dto.responses.MovieResponseDto
import com.example.kinopoisk_test_app.data.dto.responses.MoviesResponseDto
import com.example.kinopoisk_test_app.data.network.api.KinopoiskApi
import com.example.kinopoisk_test_app.util.isConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

class RetrofitNetworkClient(private val kinopoiskApi: KinopoiskApi, private val context: Context) :
    NetworkClient {
    override suspend fun getAllMovies(): Result<MoviesResponseDto> {
        if (!isConnected(context)) {
            return Result.failure(ConnectException())
        }
        val response = withContext(Dispatchers.IO) {
            try {
                val result =
                    kinopoiskApi.getAllMovies()
                Result.success(result)
            } catch (e: HttpException) {
                Result.failure(e)
            } catch (e: SocketTimeoutException) {
                Result.failure(e)
            }
        }
        return response
    }

    override suspend fun getMovieById(request: MovieRequest): Result<MovieResponseDto> {
        if (!isConnected(context)) {
            return Result.failure(ConnectException())
        }
        val response = withContext(Dispatchers.IO) {
            try {
                val result = kinopoiskApi.getMovieById(request.movieId)
                Result.success(result)
            } catch (e: HttpException) {
                Result.failure(e)
            } catch (e: SocketTimeoutException) {
                Result.failure(e)
            }
        }
        return response
    }

    override suspend fun searchMovies(request: MoviesSearchRequest): Result<MoviesResponseDto> {
        if (!isConnected(context)) {
            return Result.failure(ConnectException())
        }
        val response = withContext(Dispatchers.IO) {
            try {
                val result = kinopoiskApi.searchMovies(request.query)
                Result.success(result)
            } catch (e: HttpException) {
                Result.failure(e)
            } catch (e: SocketTimeoutException) {
                Result.failure(e)
            }
        }
        return response
    }
}