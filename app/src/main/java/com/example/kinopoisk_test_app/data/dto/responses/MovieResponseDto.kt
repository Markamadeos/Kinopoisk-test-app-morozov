package com.example.kinopoisk_test_app.data.dto.responses

import com.google.gson.annotations.SerializedName

data class MovieResponseDto(
    @SerializedName("kinopoiskHDId")
    val id: String,
    @SerializedName("nameRu")
    val name: String?,
    @SerializedName("posterUrl")
    val cover: String?,
    @SerializedName("posterUrlPreview")
    val coverSmall: String?,
    val year: Int?,
    val description: String?,
    val countries: List<CountryDto>?,
    val genres: List<GenreDto>?
)