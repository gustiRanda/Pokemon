package com.gmind.pokemon

import android.app.Application
import com.gmind.pokemon.core.di.databaseModule
import com.gmind.pokemon.core.di.networkModule
import com.gmind.pokemon.core.di.repositoryModule
import com.gmind.pokemon.di.useCaseModule
import com.gmind.pokemon.di.viewModelModule

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}