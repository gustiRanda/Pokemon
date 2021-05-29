package com.gmind.pokemon.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmind.pokemon.core.ui.PokemonAdapter
import com.gmind.pokemon.core.ui.ViewModelFactory
import com.gmind.pokemon.databinding.FragmentFavoriteBinding
import com.gmind.pokemon.detail.DetailPokemonActivity


class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val pokemonAdapter = PokemonAdapter()
            pokemonAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailPokemonActivity::class.java)
                intent.putExtra(DetailPokemonActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            val factory = ViewModelFactory.getInstance(requireActivity())
            favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

            favoriteViewModel.favoritePokemon.observe(viewLifecycleOwner, { dataPokemon ->
                pokemonAdapter.setData(dataPokemon)
                binding.viewEmpty.root.visibility = if (dataPokemon.isNotEmpty()) View.GONE else View.VISIBLE
            })

            with(binding.rvPokemon) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = pokemonAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}