package com.gmind.pokemon.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmind.pokemon.R
import com.gmind.pokemon.core.data.Resource
import com.gmind.pokemon.core.ui.PokemonAdapter
import com.gmind.pokemon.databinding.FragmentHomeBinding
import com.gmind.pokemon.detail.DetailPokemonActivity
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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

            homeViewModel.pokemon.observe(viewLifecycleOwner, { pokemon ->
                if (pokemon != null) {
                    when (pokemon) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            pokemonAdapter.setData(pokemon.data)
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvError.text = pokemon.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
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