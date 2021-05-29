package com.gmind.pokemon.core.domain.usecase

import com.gmind.pokemon.core.domain.model.Pokemon
import com.gmind.pokemon.core.domain.repository.IPokemonRepository

class PokemonInteractor(private val pokemonRepository: IPokemonRepository): PokemonUseCase {

    override fun getAllPokemon() = pokemonRepository.getAllPokemon()

    override fun getFavoritePokemon() = pokemonRepository.getFavoritePokemon()

    override fun setFavoritePokemon(pokemon: Pokemon, state: Boolean) = pokemonRepository.setFavoritePokemon(pokemon, state)
}