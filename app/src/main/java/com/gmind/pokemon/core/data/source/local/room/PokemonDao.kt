package com.gmind.pokemon.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gmind.pokemon.core.data.source.local.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    fun getAllPokemon(): LiveData<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon where isFavorite = 1")
    fun getFavoritePokemon(): LiveData<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemon(pokemon: List<PokemonEntity>)

    @Update
    fun updateFavoritePokemon(pokemon: PokemonEntity)
}
