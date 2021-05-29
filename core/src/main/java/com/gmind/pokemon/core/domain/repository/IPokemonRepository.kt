package com.gmind.pokemon.core.domain.repository

import com.gmind.pokemon.core.data.Resource
import com.gmind.pokemon.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {

    fun getAllPokemon(): Flow<Resource<List<Pokemon>>>

    fun getFavoritePokemon(): Flow<List<Pokemon>>

    fun setFavoritePokemon(pokemon: Pokemon, state: Boolean)

}