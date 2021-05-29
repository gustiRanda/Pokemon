package com.gmind.pokemon.core.data.source.local

import androidx.lifecycle.LiveData
import com.gmind.pokemon.core.data.source.local.entity.PokemonEntity
import com.gmind.pokemon.core.data.source.local.room.PokemonDao

class LocalDataSource private constructor(private val pokemonDao: PokemonDao) {

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(pokemonDao: PokemonDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(pokemonDao)
            }
    }

    fun getAllPokemon(): LiveData<List<PokemonEntity>> = pokemonDao.getAllPokemon()

    fun getFavoritePokemon(): LiveData<List<PokemonEntity>> = pokemonDao.getFavoritePokemon()

    fun insertPokemon(pokemonList: List<PokemonEntity>) = pokemonDao.insertPokemon(pokemonList)

    fun setFavoritePokemon(pokemon: PokemonEntity, newState: Boolean) {
        pokemon.isFavorite = newState
        pokemonDao.updateFavoritePokemon(pokemon)
    }
}

