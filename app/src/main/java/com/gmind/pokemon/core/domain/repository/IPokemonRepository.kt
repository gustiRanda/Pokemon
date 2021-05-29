package com.gmind.pokemon.core.domain.repository

import androidx.lifecycle.LiveData
import com.gmind.pokemon.core.data.Resource
import com.gmind.pokemon.core.domain.model.Pokemon

interface IPokemonRepository {

    fun getAllPokemon(): LiveData<Resource<List<Pokemon>>>

    fun getFavoritePokemon(): LiveData<List<Pokemon>>

    fun setFavoritePokemon(pokemon: Pokemon, state: Boolean)

}