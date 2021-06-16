package com.gmind.pokemon.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.gmind.pokemon.R
import com.gmind.pokemon.core.domain.model.Pokemon
import com.gmind.pokemon.databinding.ActivityDetailPokemonBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailPokemonActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private val detailPokemonViewModel: DetailPokemonViewModel by viewModel()
    private lateinit var binding: ActivityDetailPokemonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val detailPokemon = intent.getParcelableExtra<Pokemon>(EXTRA_DATA)
        showDetailPokemon(detailPokemon)
    }

    private fun showDetailPokemon(detailPokemon: Pokemon?) {
        detailPokemon?.let {
            supportActionBar?.title = detailPokemon.name
            binding.content.tvCandyType.text = "Candy Type = ${detailPokemon.candy}"
            binding.content.tvHeight.text = "Height = ${detailPokemon.height}"
            binding.content.tvWeight.text = "Weight= ${detailPokemon.weight}"
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