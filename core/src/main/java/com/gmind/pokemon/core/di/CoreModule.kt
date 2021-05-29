package com.gmind.pokemon.core.di

import androidx.room.Room
import com.gmind.pokemon.core.data.PokemonRepository
import com.gmind.pokemon.core.data.source.local.LocalDataSource
import com.gmind.pokemon.core.data.source.local.room.PokemonDatabase
import com.gmind.pokemon.core.data.source.remote.RemoteDataSource
import com.gmind.pokemon.core.data.source.remote.network.ApiService
import com.gmind.pokemon.core.domain.repository.IPokemonRepository
import com.gmind.pokemon.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



val databaseModule = module {
    factory {
        get<PokemonDatabase>().pokemonDao()
    }

    single {
        Room.databaseBuilder(
                androidContext(),
                PokemonDatabase::class.java,
                "Pokemon.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
    }

    single {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single {
        LocalDataSource(get())
    }

    single {
        RemoteDataSource(get())
    }

    factory {
        AppExecutors()
    }

    single<IPokemonRepository> {
        PokemonRepository(get(), get(), get())
    }

}