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
    private var layoutViewType: LayoutViews/* = LayoutViews.GRID_LAYOUT*/,
    private var onClick: (SearchItem) -> Unit
) :
    RecyclerView.Adapter<HomeAdapter.Holder>() {
    lateinit var listBinding: MovieRowItemBinding
    lateinit var gridItemBinding: MovieGridItemBinding
    var view: View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var listViewBinding: MovieRowItemBinding = MovieRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        var gridViewBinding: MovieGridItemBinding = MovieGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        when (layoutViewType) {
            LayoutViews.GRID_LAYOUT -> { view = gridViewBinding.root }
            LayoutViews.LIST_LAYOUT -> { view = listViewBinding.root }
        }
        var holder: Holder = Holder(onClick, view!!, layoutViewType)
        holder.setViewDataBinding(listViewBinding, gridViewBinding)

        return holder

    }
    override fun onBindViewHolder(holder: Holder, position: Int) { holder.bind(searchResults[position]) }

    override fun getItemCount(): Int { return searchResults.size }


    class Holder(
        private val itemOnClick: (SearchItem) -> Unit,
        private var view: View,
        private var layoutViewType: LayoutViews/* = LayoutViews.GRID_LAYOUT*/
    ) : RecyclerView.ViewHolder(view) {

        private lateinit var listViewBindingT: MovieRowItemBinding
        private lateinit var gridViewBindingT: MovieGridItemBinding
        fun setViewDataBinding(listViewBinding: MovieRowItemBinding, gridViewBinding: MovieGridItemBinding) {
            listViewBindingT = listViewBinding
            gridViewBindingT = gridViewBinding
        }

        fun bind(searchItem: SearchItem) {
            when (layoutViewType) {
                LayoutViews.GRID_LAYOUT -> gridViewBindingT.searchItem = searchItem
                LayoutViews.LIST_LAYOUT -> listViewBindingT.searchItem = searchItem
            }

            itemView.setOnClickListener {
                itemOnClick(searchItem)
            }

        }

    }



    @SuppressLint("NotifyDataSetChanged")
    fun sortAToZ() {
        var mutable: MutableList<SearchItem> = searchResults.toMutableList()
        mutable.sortBy { it.title }
        searchResults = mutable.toList() as ArrayList<SearchItem>
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortZToA() {
        var mutable: MutableList<SearchItem> = searchResults.toMutableList()
        mutable.sortBy { it.title }
        mutable.reverse()
        searchResults = mutable.toList() as ArrayList<SearchItem>
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortOldToNew() {
        var mutable: MutableList<SearchItem> = searchResults.toMutableList()
        mutable.sortBy { it.year }
        searchResults = mutable.toList() as ArrayList<SearchItem>
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortNewToOld() {
        var mutable: MutableList<SearchItem> = searchResults.toMutableList()
        mutable.sortBy { it.year }
        mutable.reverse()
        searchResults = mutable.toList() as ArrayList<SearchItem>
        notifyDataSetChanged()
    }


}




