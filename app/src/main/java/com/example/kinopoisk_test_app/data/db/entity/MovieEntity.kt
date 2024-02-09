package com.example.kinopoisk_test_app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: String,
    val name: String = EMPTY_PARAM,
    val cover: String = EMPTY_PARAM,
    val coverSmall: String = EMPTY_PARAM,
    val year: String = EMPTY_PARAM,
    val description: String = EMPTY_PARAM,
    val countries: String = EMPTY_PARAM,
    val genres: String = EMPTY_PARAM
) {
    companion object {
        private const val EMPTY_PARAM = ""
    }
}