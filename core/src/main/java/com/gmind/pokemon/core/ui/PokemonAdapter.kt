package com.gmind.pokemon.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmind.pokemon.core.R
import com.gmind.pokemon.core.databinding.ItemListPokemonBinding
import com.gmind.pokemon.core.domain.model.Pokemon
import java.util.*

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.ListViewHolder>() {

    private var listData = ArrayList<Pokemon>()
    var onItemClick: ((Pokemon) -> Unit)? = null

    fun setData(newListData: List<Pokemon>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_pokemon, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListPokemonBinding.bind(itemView)
        fun bind(data: Pokemon) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.img)
//                        .apply(RequestOptions.overrideOf(600))
                    .into(ivItemImage)
                tvItemTitle.text = data.name
                tvItemSubtitle.text = data.id
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}