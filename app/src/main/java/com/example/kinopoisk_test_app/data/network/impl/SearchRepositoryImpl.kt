package com.example.kinopoisk_test_app.data.network.impl

import com.example.kinopoisk_test_app.R
import com.example.kinopoisk_test_app.data.network.NetworkClient
import com.example.kinopoisk_test_app.data.network.converters.MovieDtoConverter
import com.example.kinopoisk_test_app.data.network.dto.requests.MovieRequest
import com.example.kinopoisk_test_app.data.network.dto.requests.MoviesSearchRequest
import com.example.kinopoisk_test_app.data.network.dto.responses.MovieResponseDto
import com.example.kinopoisk_test_app.domian.api.SearchRepository
import com.example.kinopoisk_test_app.domian.models.Movie
import com.example.kinopoisk_test_app.domian.models.SearchResultData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val converter: MovieDtoConverter
) : SearchRepository {
    override suspend fun getPopularMovies(): Flow<SearchResultData<List<Movie>>> = flow {
        val searchResult = networkClient.getAllMovies()
        val data = searchResult.getOrNull()
        val error = searchResult.exceptionOrNull()
        when {
            data?.items != null -> {
                emit(SearchResultData.Data(convertFromMovieDto(data.items)))
            }

            error is ConnectException -> {
                emit(SearchResultData.NoInternet(R.string.no_internet))
            }

            error is SocketTimeoutException -> {
                emit(SearchResultData.NoInternet(R.string.no_internet))
            }

            error is HttpException -> {
                emit(SearchResultData.ServerError(R.string.server_error))
            }
        }
    }

    override suspend fun getMovieById(movieId: String): Flow<SearchResultData<Movie>> = flow {
        val result = networkClient.getMovieById(MovieRequest(movieId))
        val data = result.getOrNull()
        val error = result.exceptionOrNull()
        when {
            data != null -> {
                emit(SearchResultData.Data(converter.map(data)))
            }

            error is ConnectException -> {
                emit(SearchResultData.NoInternet(R.string.no_internet))
            }

            error is SocketTimeoutException -> {
                emit(SearchResultData.NoInternet(R.string.no_internet))
            }

            error is HttpException -> {
                emit(SearchResultData.ServerError(R.string.server_error))
            }
        }
    }

    override suspend fun searchMovies(query: String): Flow<SearchResultData<List<Movie>>> = flow {
        val searchResult = networkClient.searchMovies(MoviesSearchRequest(query))
        val data = searchResult.getOrNull()
        val error = searchResult.exceptionOrNull()
        when {
            data?.items != null -> {
                emit(SearchResultData.Data(convertFromMovieDto(data.items)))
            }

            error is ConnectException -> {
                emit(SearchResultData.NoInternet(R.string.no_internet))
            }

            error is SocketTimeoutException -> {
                emit(SearchResultData.NoInternet(R.string.no_internet))
            }

            error is HttpException -> {
                emit(SearchResultData.ServerError(R.string.server_error))
            }
        }
    }

    private fun convertFromMovieDto(moviesDto: List<MovieResponseDto>): List<Movie> {
        return moviesDto.map { movieResponseDto -> converter.map(movieResponseDto) }
    }
}
