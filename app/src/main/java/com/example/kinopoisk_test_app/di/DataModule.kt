package com.example.kinopoisk_test_app.di

import com.example.kinopoisk_test_app.data.network.api.KinopoiskApi
import com.example.kinopoisk_test_app.util.BASE_URL
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
}