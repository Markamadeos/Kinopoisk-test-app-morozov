package com.example.kinopoisk_test_app.data.db

import androidx.room.RoomDatabase
import com.example.kinopoisk_test_app.data.db.dao.MovieDao

abstract class AppDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}