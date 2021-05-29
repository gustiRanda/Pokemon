package com.gmind.pokemon.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.gmind.pokemon.R
import com.gmind.pokemon.core.domain.model.Pokemon
import com.gmind.pokemon.core.ui.ViewModelFactory
import com.gmind.pokemon.databinding.ActivityDetailPokemonBinding

class DetailPokemonActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var detailPokemonViewModel: DetailPokemonViewModel
    private lateinit var binding: ActivityDetailPokemonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val factory = ViewModelFactory.getInstance(this)
        detailPokemonViewModel = ViewModelProvider(this, factory)[DetailPokemonViewModel::class.java]

        val detailPokemon = intent.getParcelableExtra<Pokemon>(EXTRA_DATA)
        showDetailPokemon(detailPokemon)
    }

    private fun showDetailPokemon(detailPokemon: Pokemon?) {
        detailPokemon?.let {
            supportActionBar?.title = detailPokemon.name
            binding.content.tvDetailDescription.text = detailPokemon.id
            Glide.with(this@DetailPokemonActivity)
                .load(detailPokemon.img)
                .into(binding.ivDetailImage)

            var statusFavorite = detailPokemon.isFavorite
            setStatusFavorite(statusFavorite)
            binding.fab.setOnClickListener {
                statusFavorite = !statusFavorite
                detailPokemonViewModel.setFavoritePokemon(detailPokemon, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite_white))
        }
    }
}