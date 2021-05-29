package com.gmind.pokemon.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gmind.pokemon.core.domain.usecase.PokemonUseCase

class FavoriteViewModel(pokemonUseCase: PokemonUseCase) : ViewModel() {
    val favoritePokemon = pokemonUseCase.getFavoritePokemon().asLiveData()
}