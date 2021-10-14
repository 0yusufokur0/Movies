package com.resurrection.movies.ui.main.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.resurrection.movies.R
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.databinding.FragmentFavoriteBinding
import com.resurrection.movies.ui.base.BaseFragment
import com.resurrection.movies.ui.main.detail.DetailFragment
import com.resurrection.movies.ui.main.home.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
        private val viewModel: FavoriteViewModel by viewModels()
    var adapter: HomeAdapter? = null
    private var searchResultsList: ArrayList<SearchItem> = ArrayList()
    private var searchItemDetail: DetailFragment? = null
    var toast: Toast? = null
    override fun getLayoutRes(): Int {
        return R.layout.fragment_favorite
    }

    override fun init(savedInstanceState: Bundle?) {
        viewModel.getAllFavoriteMovies()
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            binding.favoriteRecyclerview.layoutManager = GridLayoutManager(requireContext(),2)

            adapter = HomeAdapter(it as ArrayList<SearchItem>) {
                searchItemDetail = DetailFragment()
                val bundle = Bundle()
                bundle.putString("cryptoId", it.imdbID)
                searchItemDetail!!.arguments = bundle
                searchItemDetail!!.show(parentFragmentManager, "Bottom Sheet")
            }
            binding.favoriteRecyclerview.adapter = adapter
            binding.progressBar.visibility = View.GONE
        })
    }


}