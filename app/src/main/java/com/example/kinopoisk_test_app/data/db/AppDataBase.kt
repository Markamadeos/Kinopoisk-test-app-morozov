package com.example.kinopoisk_test_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kinopoisk_test_app.data.db.dao.MovieDao
import com.example.kinopoisk_test_app.data.db.entity.MovieEntity

@Database(
    version = 1,
    entities = [MovieEntity::class]
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}