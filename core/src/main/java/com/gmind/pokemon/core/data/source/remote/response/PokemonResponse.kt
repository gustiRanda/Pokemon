package com.gmind.pokemon.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @field:SerializedName("candy")
    var candy: String,

    @field:SerializedName("height")
    var height: String,

    @field:SerializedName("id")
    var id: String,

    @field:SerializedName("img")
    var img: String,

    @field:SerializedName("name")
    var name: String,

    @field:SerializedName("weight")
    var weight: String
)