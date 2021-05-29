package com.gmind.pokemon.core.data.source.local.room

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gmind.pokemon.core.data.source.local.entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {
        @Volatile
        private var INSTANCE: PokemonDatabase? = null

        fun getInstance(context: Context): PokemonDatabase =
            INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                PokemonDatabase::class.java,
                "Pokemon.db"
            )
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            instance
        }
    }
}