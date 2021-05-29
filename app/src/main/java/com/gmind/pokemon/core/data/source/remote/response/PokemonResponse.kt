package com.gmind.pokemon.core.data.source.remote.response

data class PokemonResponse(

    var candy: String,
    var candy_count: Int,
    var egg: String,
    var height: String,
    var id: String,
    var img: String,
    var multipliers: List<Double>,
    var name: String,
    var next_evolution: List<NextEvolution>,
    var num: String,
    var prev_evolution: List<PrevEvolution>,
    var spawn_chance: Double,
    var spawn_time: String,
    var type: List<String>,
    var weaknesses: List<String>,
    var weight: String
)