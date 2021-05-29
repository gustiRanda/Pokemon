package com.gmind.pokemon.core.data.source.local

import com.gmind.pokemon.core.data.source.local.entity.PokemonEntity
import com.gmind.pokemon.core.data.source.local.room.PokemonDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val pokemonDao: PokemonDao) {

    fun getAllPokemon(): Flow<List<PokemonEntity>> = pokemonDao.getAllPokemon()

    fun getFavoritePokemon(): Flow<List<PokemonEntity>> = pokemonDao.getFavoritePokemon()

    suspend fun insertPokemon(pokemonList: List<PokemonEntity>) = pokemonDao.insertPokemon(pokemonList)

    fun setFavoritePokemon(pokemon: PokemonEntity, newState: Boolean) {
        pokemon.isFavorite = newState
        pokemonDao.updateFavoritePokemon(pokemon)
    }
}

