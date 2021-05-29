package com.gmind.pokemon.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.gmind.pokemon.core.data.source.local.LocalDataSource
import com.gmind.pokemon.core.data.source.remote.RemoteDataSource
import com.gmind.pokemon.core.data.source.remote.network.ApiResponse
import com.gmind.pokemon.core.data.source.remote.response.PokemonResponse
import com.gmind.pokemon.core.domain.model.Pokemon
import com.gmind.pokemon.core.domain.repository.IPokemonRepository
import com.gmind.pokemon.core.utils.AppExecutors
import com.gmind.pokemon.core.utils.DataMapper

class PokemonRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IPokemonRepository {

    companion object {
        @Volatile
        private var instance: PokemonRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): PokemonRepository =
            instance ?: synchronized(this) {
                instance ?: PokemonRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllPokemon(): LiveData<Resource<List<Pokemon>>> =
        object : NetworkBoundResource<List<Pokemon>, List<PokemonResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Pokemon>> {
                return Transformations.map(localDataSource.getAllPokemon()) {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Pokemon>?): Boolean =
//                data == null || data.isEmpty()
                 true // ganti dengan true jika ingin selalu mengambil data dari internet

            override fun createCall(): LiveData<ApiResponse<List<PokemonResponse>>> =
                remoteDataSource.getAllPokemon()

            override fun saveCallResult(data: List<PokemonResponse>) {
                val pokemonList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertPokemon(pokemonList)
            }
        }.asLiveData()

    override fun getFavoritePokemon(): LiveData<List<Pokemon>> {
        return Transformations.map(localDataSource.getFavoritePokemon()) {
           DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoritePokemon(pokemon: Pokemon, state: Boolean) {
        val pokemonEntity = DataMapper.mapDomainToEntity(pokemon)
        appExecutors.diskIO().execute { localDataSource.setFavoritePokemon(pokemonEntity, state) }
    }

}

