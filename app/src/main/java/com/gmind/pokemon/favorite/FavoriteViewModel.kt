package com.gmind.pokemon.favorite

import androidx.lifecycle.ViewModel
import com.gmind.pokemon.core.domain.usecase.PokemonUseCase

class FavoriteViewModel(pokemonUseCase: PokemonUseCase) : ViewModel() {
    val favoritePokemon = pokemonUseCase.getFavoritePokemon()
}