package com.example.kinopoisk_test_app.app

import android.app.Application
import com.example.kinopoisk_test_app.di.dataModule
import com.example.kinopoisk_test_app.di.domainModule
import com.example.kinopoisk_test_app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, domainModule, viewModelModule)
        }
    }
}