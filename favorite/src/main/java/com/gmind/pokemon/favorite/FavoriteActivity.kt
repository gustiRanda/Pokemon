package com.gmind.pokemon.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmind.pokemon.core.ui.PokemonAdapter
import com.gmind.pokemon.detail.DetailPokemonActivity
import com.gmind.pokemon.favorite.databinding.ActivityFavoriteBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {


    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)

        supportActionBar?.title = "Pokemon Favorite"

        val pokemonAdapter = PokemonAdapter()
        pokemonAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailPokemonActivity::class.java)
            intent.putExtra(DetailPokemonActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        favoriteViewModel.favoritePokemon.observe(this, { dataPokemon ->
            pokemonAdapter.setData(dataPokemon)
            binding.empty.visibility = if (dataPokemon.isNotEmpty()) View.GONE else View.VISIBLE

//            binding.viewEmpty.root.visibility = if (dataPokemon.isNotEmpty()) View.GONE else View.VISIBLE
        })

        with(binding.rvPokemon) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = pokemonAdapter
        }
    }
}