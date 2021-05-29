package com.gmind.pokemon.core.data

import com.gmind.pokemon.core.data.source.local.LocalDataSource
import com.gmind.pokemon.core.data.source.remote.RemoteDataSource
import com.gmind.pokemon.core.data.source.remote.network.ApiResponse
import com.gmind.pokemon.core.data.source.remote.response.PokemonResponse
import com.gmind.pokemon.core.domain.model.Pokemon
import com.gmind.pokemon.core.domain.repository.IPokemonRepository
import com.gmind.pokemon.core.utils.AppExecutors
import com.gmind.pokemon.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IPokemonRepository {

    override fun getAllPokemon(): Flow<Resource<List<Pokemon>>> =
        object : NetworkBoundResource<List<Pokemon>, List<PokemonResponse>>() {
            override fun loadFromDB(): Flow<List<Pokemon>> {
                return localDataSource.getAllPokemon().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Pokemon>?): Boolean =
                data == null || data.isEmpty()
//                 true // ganti dengan true jika ingin selalu mengambil data dari internet

            override suspend fun createCall(): Flow<ApiResponse<List<PokemonResponse>>> =
                remoteDataSource.getAllPokemon()

            override suspend fun saveCallResult(data: List<PokemonResponse>) {
                val pokemonList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertPokemon(pokemonList)
            }
        }.asFLow()

    override fun getFavoritePokemon(): Flow<List<Pokemon>> {
        return localDataSource.getFavoritePokemon().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoritePokemon(pokemon: Pokemon, state: Boolean) {
        val pokemonEntity = DataMapper.mapDomainToEntity(pokemon)
        appExecutors.diskIO().execute { localDataSource.setFavoritePokemon(pokemonEntity, state) }
    }

}

