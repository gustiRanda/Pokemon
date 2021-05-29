package com.gmind.pokemon.detail

import androidx.lifecycle.ViewModel
import com.gmind.pokemon.core.domain.model.Pokemon
import com.gmind.pokemon.core.domain.usecase.PokemonUseCase

class DetailPokemonViewModel(private val pokemonUseCase: PokemonUseCase) : ViewModel() {

    fun setFavoritePokemon(pokemon: Pokemon, newStatus:Boolean) =
        pokemonUseCase.setFavoritePokemon(pokemon, newStatus)
}