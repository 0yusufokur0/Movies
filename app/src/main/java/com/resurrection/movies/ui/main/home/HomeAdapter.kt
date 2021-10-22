package com.resurrection.movies.ui.main.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.databinding.MovieGridItemBinding
import com.resurrection.movies.databinding.MovieRowItemBinding
import com.resurrection.movies.util.LayoutViews

class HomeAdapter(
    private var searchResults: ArrayList<SearchItem>,
    private var layoutViewType: LayoutViews,
    private var onClick: (SearchItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    var view: View? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (layoutViewType) {
            LayoutViews.LIST_LAYOUT -> {
                val listViewBinding =
                    MovieRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ListHolder(listViewBinding, onClick)
            }
            LayoutViews.GRID_LAYOUT -> {
                val gridViewBinding =
                    MovieGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridHolder(gridViewBinding, onClick)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (layoutViewType) {
            LayoutViews.GRID_LAYOUT -> (holder as GridHolder).bind(searchResults[position])
            LayoutViews.LIST_LAYOUT -> (holder as ListHolder).bind(searchResults[position])
        }
    }

    override fun getItemCount(): Int {
        return searchResults.size
    }

    class ListHolder(
        private var listViewBinding: MovieRowItemBinding,
        private val itemOnClick: (SearchItem) -> Unit
    ) : RecyclerView.ViewHolder(listViewBinding.root) {
        fun bind(searchItem: SearchItem) {
            listViewBinding.searchItem = searchItem
            itemView.setOnClickListener { itemOnClick(searchItem) }
        }
    }

    class GridHolder(
        private var gridViewBinding: MovieGridItemBinding,
        private val itemOnClick: (SearchItem) -> Unit
    ) : RecyclerView.ViewHolder(gridViewBinding.root) {

        fun bind(searchItem: SearchItem) {
            gridViewBinding.searchItem = searchItem
            itemView.setOnClickListener { itemOnClick(searchItem) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeItemLayout(layoutType: LayoutViews) {
        layoutViewType = layoutType
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortAToZ() {
        if (searchResults.size != 1) {
            val mutable: MutableList<SearchItem> = searchResults.toMutableList()
            mutable.sortBy { it.title }
            searchResults = mutable.toList() as ArrayList<SearchItem>
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortZToA() {
        if (searchResults.size != 1) {
            val mutable: MutableList<SearchItem> = searchResults.toMutableList()
            mutable.sortBy { it.title }
            mutable.reverse()
            searchResults = mutable.toList() as ArrayList<SearchItem>
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortOldToNew() {
        if (searchResults.size != 1) {
            val mutable: MutableList<SearchItem> = searchResults.toMutableList()
            mutable.sortBy { it.year }
            searchResults = mutable.toList() as ArrayList<SearchItem>
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortNewToOld() {
        if (searchResults.size != 1) {
            val mutable: MutableList<SearchItem> = searchResults.toMutableList()
            mutable.sortBy { it.year }
            mutable.reverse()
            searchResults = mutable.toList() as ArrayList<SearchItem>
            notifyDataSetChanged()
        }
    }
}




