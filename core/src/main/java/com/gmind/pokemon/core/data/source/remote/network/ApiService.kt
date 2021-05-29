package com.gmind.pokemon.core.data.source.remote.network

import com.gmind.pokemon.core.data.source.remote.response.ListPokemonResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("pokedex.json")
    suspend fun getList(): ListPokemonResponse
}
