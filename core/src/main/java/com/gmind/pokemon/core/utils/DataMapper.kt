package com.gmind.pokemon.core.utils

import com.gmind.pokemon.core.data.source.local.entity.PokemonEntity
import com.gmind.pokemon.core.data.source.remote.response.PokemonResponse
import com.gmind.pokemon.core.domain.model.Pokemon

object DataMapper {
    fun mapResponsesToEntities(input: List<PokemonResponse>): List<PokemonEntity> {
        val pokemonList = ArrayList<PokemonEntity>()
        input.map {
            val pokemon = PokemonEntity(
                pokemonId = it.id,
                name = it.name,
                image = it.img,
                candy = it.candy,
                height = it.height,
                weight = it.weight,
                isFavorite = false
            )
            pokemonList.add(pokemon)
        }
        return pokemonList
    }

    fun mapEntitiesToDomain(input: List<PokemonEntity>): List<Pokemon> =
        input.map {
            Pokemon(
                id = it.pokemonId,
                name = it.name,
                img = it.image,
                candy = it.candy,
                height = it.height,
                weight = it.weight,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Pokemon) = PokemonEntity(
        pokemonId = input.id,
        name = input.name,
        image = input.img,
        candy = input.candy,
        height = input.height,
        weight = input.weight,
        isFavorite = input.isFavorite
    )
}