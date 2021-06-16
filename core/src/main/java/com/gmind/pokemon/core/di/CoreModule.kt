package com.gmind.pokemon.core.di

import androidx.room.Room
import com.gmind.pokemon.core.data.PokemonRepository
import com.gmind.pokemon.core.data.source.local.LocalDataSource
import com.gmind.pokemon.core.data.source.local.room.PokemonDatabase
import com.gmind.pokemon.core.data.source.remote.RemoteDataSource
import com.gmind.pokemon.core.data.source.remote.network.ApiService
import com.gmind.pokemon.core.domain.repository.IPokemonRepository
import com.gmind.pokemon.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passphrase: ByteArray = SQLiteDatabase.getBytes("gmind".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
                androidContext(),
                PokemonDatabase::class.java,
                "Pokemon.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "raw.githubusercontent.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/xlDAST56PmiT3SR0WdFOR3dghwJrQ8yXx6JLSqTIRpk=")
            .add(hostname, "sha256/k2v657xBsOVe1PQRwOsHsw3bsGT2VzIqz5K+59sNQws=")
            .add(hostname, "sha256/WoiWRyIOVNa9ihaBciRSC7XHjliYS9VwUGOIud4PB18=")
            .build()

        OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .certificatePinner(certificatePinner)
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