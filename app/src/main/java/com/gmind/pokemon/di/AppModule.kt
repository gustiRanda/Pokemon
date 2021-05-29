package com.gmind.pokemon.di

import com.gmind.pokemon.core.domain.usecase.PokemonInteractor
import com.gmind.pokemon.core.domain.usecase.PokemonUseCase
import com.gmind.pokemon.detail.DetailPokemonViewModel
import com.gmind.pokemon.favorite.FavoriteViewModel
import com.gmind.pokemon.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<PokemonUseCase> {
        PokemonInteractor(get())
    }
}

val viewModelModule = module {
    viewModel{
        HomeViewModel(get())
    }

    viewModel {
        FavoriteViewModel(get())
    }

    viewModel {
        DetailPokemonViewModel(get())
    }
}