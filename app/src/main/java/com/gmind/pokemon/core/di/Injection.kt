package com.gmind.pokemon.core.di

import android.content.Context
import com.gmind.pokemon.core.data.PokemonRepository
import com.gmind.pokemon.core.data.source.local.LocalDataSource
import com.gmind.pokemon.core.data.source.local.room.PokemonDatabase
import com.gmind.pokemon.core.data.source.remote.RemoteDataSource
import com.gmind.pokemon.core.data.source.remote.network.ApiConfig
import com.gmind.pokemon.core.domain.repository.IPokemonRepository
import com.gmind.pokemon.core.domain.usecase.PokemonInteractor
import com.gmind.pokemon.core.domain.usecase.PokemonUseCase
import com.gmind.pokemon.core.utils.AppExecutors

object Injection {
    private fun provideRepository(context: Context): IPokemonRepository {
        val database = PokemonDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.pokemonDao())
        val appExecutors = AppExecutors()

        return PokemonRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun providePokemonUseCase(context: Context): PokemonUseCase {
        val repository = provideRepository(context)
        return PokemonInteractor(repository)
    }
}
