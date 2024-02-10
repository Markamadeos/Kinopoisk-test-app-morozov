package com.example.kinopoisk_test_app.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kinopoisk_test_app.data.db.entity.MovieEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Query("SELECT * FROM movies_table")
    suspend fun getMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies_table WHERE id = :movieId")
    suspend fun getMovie(movieId: String): MovieEntity?

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Query("SELECT id FROM movies_table")
    suspend fun getIds(): List<String>
}