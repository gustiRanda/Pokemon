package com.gmind.pokemon.home

import androidx.lifecycle.ViewModel
import com.gmind.pokemon.core.domain.usecase.PokemonUseCase

class HomeViewModel(pokemonUseCase: PokemonUseCase) : ViewModel() {
    val pokemon = pokemonUseCase.getAllPokemon()
}