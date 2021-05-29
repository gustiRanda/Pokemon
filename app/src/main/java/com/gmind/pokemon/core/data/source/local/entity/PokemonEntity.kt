package com.gmind.pokemon.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "pokemonId")
    var pokemonId: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)
