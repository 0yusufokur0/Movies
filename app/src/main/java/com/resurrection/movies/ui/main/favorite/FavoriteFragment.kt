package com.resurrection.movies.ui.main.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
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
    private var adapter: HomeAdapter? = null
    private var searchItemDetail: DetailFragment? = null
    override fun getLayoutRes(): Int {
        return R.layout.fragment_favorite
    }

    override fun init(savedInstanceState: Bundle?) {
        viewModel.getAllFavoriteMovies()
        setViewModelsObserve()
        binding.swipeResfresLayout.setOnRefreshListener { viewModel.getAllFavoriteMovies() }
    }

    private fun setViewModelsObserve() {
        viewModel.movies.observe(viewLifecycleOwner, { it ->
            binding.favoriteRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)

            adapter = HomeAdapter(it as ArrayList<SearchItem>) {
                searchItemDetail = DetailFragment()
                val bundle = Bundle()
                bundle.putString("cryptoId", it.imdbID)
                searchItemDetail!!.arguments = bundle
                searchItemDetail!!.show(parentFragmentManager, "Bottom Sheet")
            }
            binding.favoriteRecyclerview.adapter = adapter
            binding.progressBar.visibility = View.GONE
            binding.swipeResfresLayout.isRefreshing = false
        })
    }

}