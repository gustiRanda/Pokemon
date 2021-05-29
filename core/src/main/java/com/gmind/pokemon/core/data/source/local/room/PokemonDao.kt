package com.gmind.pokemon.core.data.source.local.room

import androidx.room.*
import com.gmind.pokemon.core.data.source.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    fun getAllPokemon(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon where isFavorite = 1")
    fun getFavoritePokemon(): Flow<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: List<PokemonEntity>)

    @Update
    fun updateFavoritePokemon(pokemon: PokemonEntity)
}
