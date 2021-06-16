package com.gmind.pokemon.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListPokemonResponse(

    @field:SerializedName("pokemon")
    val pokemon: List<PokemonResponse>
)