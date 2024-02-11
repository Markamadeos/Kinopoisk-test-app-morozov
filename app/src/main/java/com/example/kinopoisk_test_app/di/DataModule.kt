package com.example.kinopoisk_test_app.di

import androidx.room.Room
import com.example.kinopoisk_test_app.data.db.AppDataBase
import com.example.kinopoisk_test_app.data.db.converters.MovieDbConverter
import com.example.kinopoisk_test_app.data.db.impl.FavoriteRepositoryImpl
import com.example.kinopoisk_test_app.data.network.NetworkClient
import com.example.kinopoisk_test_app.data.network.api.KinopoiskApi
import com.example.kinopoisk_test_app.data.network.converters.MovieDtoConverter
import com.example.kinopoisk_test_app.data.network.impl.RetrofitNetworkClient
import com.example.kinopoisk_test_app.data.network.impl.SearchRepositoryImpl
import com.example.kinopoisk_test_app.domian.api.FavoriteRepository
import com.example.kinopoisk_test_app.domian.api.SearchRepository
import com.example.kinopoisk_test_app.util.BASE_URL
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single<KinopoiskApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KinopoiskApi::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(kinopoiskApi = get(), context = androidContext())
    }

    factory { Gson() }

    single {
        Room.databaseBuilder(androidContext(), AppDataBase::class.java, "database.db")
            .build()
    }

    factory { MovieDtoConverter() }

    factory { MovieDbConverter() }

    single<SearchRepository> {
        SearchRepositoryImpl(converter = get(), networkClient = get(), appDataBase = get())
    }

    single<FavoriteRepository> {
        FavoriteRepositoryImpl(converter = get(), appDataBase = get())
    }
}