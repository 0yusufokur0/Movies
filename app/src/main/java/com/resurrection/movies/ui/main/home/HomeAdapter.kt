package com.resurrection.movies.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.databinding.MovieItemBinding

class HomeAdapter(private var searchResults: ArrayList<SearchItem>, private var onClick: (SearchItem) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClick
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val cryptoMarketModel = searchResults[position]
        holder.bind(cryptoMarketModel)
    }

    override fun getItemCount(): Int {
        return searchResults.size
    }

    class Holder(
        private var binding: MovieItemBinding,
        private val itemOnClick: (SearchItem) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(searchItem: SearchItem) {
            binding.searchItem = searchItem
            itemView.setOnClickListener {
                itemOnClick(searchItem)
            }
        }

    }
}