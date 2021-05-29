package com.gmind.pokemon.core.domain.usecase

import androidx.lifecycle.LiveData
import com.gmind.pokemon.core.data.Resource
import com.gmind.pokemon.core.domain.model.Pokemon

interface PokemonUseCase {
    fun getAllPokemon(): LiveData<Resource<List<Pokemon>>>
    fun getFavoritePokemon(): LiveData<List<Pokemon>>
    fun setFavoritePokemon(pokemon: Pokemon, state: Boolean)
}