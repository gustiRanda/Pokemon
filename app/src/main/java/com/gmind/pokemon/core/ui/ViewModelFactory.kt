package com.gmind.pokemon.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gmind.pokemon.core.di.Injection
import com.gmind.pokemon.core.domain.usecase.PokemonUseCase
import com.gmind.pokemon.detail.DetailPokemonViewModel
import com.gmind.pokemon.favorite.FavoriteViewModel
import com.gmind.pokemon.home.HomeViewModel

class ViewModelFactory private constructor(private val pokemonUseCase: PokemonUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.providePokemonUseCase(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(pokemonUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(pokemonUseCase) as T
            }
            modelClass.isAssignableFrom(DetailPokemonViewModel::class.java) -> {
                DetailPokemonViewModel(pokemonUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}