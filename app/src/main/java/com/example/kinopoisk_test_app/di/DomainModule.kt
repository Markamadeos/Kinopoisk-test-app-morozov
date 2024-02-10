package com.example.kinopoisk_test_app.di

import com.example.kinopoisk_test_app.domian.api.SearchInterctor
import com.example.kinopoisk_test_app.domian.impl.SearchInteractorImpl
import org.koin.dsl.module

val domainModule = module {
    factory<SearchInterctor> {
        SearchInteractorImpl(searchRepository = get())
    }
}