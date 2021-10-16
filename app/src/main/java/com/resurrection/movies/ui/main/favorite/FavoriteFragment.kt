package com.resurrection.movies.ui.main.favorite

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.resurrection.movies.R
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.databinding.FragmentFavoriteBinding
import com.resurrection.movies.ui.base.BaseFragment
import com.resurrection.movies.ui.main.detail.DetailFragment
import com.resurrection.movies.ui.main.home.HomeAdapter
import com.resurrection.movies.util.LayoutViews
import com.resurrection.movies.util.Status.*
import com.resurrection.movies.util.isNetworkAvailable
import com.resurrection.movies.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    private val viewModel: FavoriteViewModel by viewModels()
    private var adapter: HomeAdapter? = null
    private var searchItemDetail: DetailFragment? = null
    override fun getLayoutRes(): Int {
        return R.layout.fragment_favorite
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun init(savedInstanceState: Bundle?) {
        viewModel.getAllFavoriteMovies()
        isNetworkAvailable(requireContext())
        setViewModelsObserve()
        binding.swipeResfresLayout.setOnRefreshListener { viewModel.getAllFavoriteMovies() }
    }

    private fun setViewModelsObserve() {
        viewModel.movies.observe(viewLifecycleOwner, { it ->
            when (it.status) {
                SUCCESS -> {
                    it.data?.let {
                        binding.favoriteRecyclerview.layoutManager =
                            GridLayoutManager(requireContext(), 2)

                        adapter = HomeAdapter(it as ArrayList<SearchItem>,LayoutViews.GRID_LAYOUT) {
                            searchItemDetail = DetailFragment()
                            val bundle = Bundle()
                            bundle.putString("movieId", it.imdbID)
                            searchItemDetail!!.arguments = bundle
                            searchItemDetail!!.show(parentFragmentManager, "Bottom Sheet")
                        }
                        binding.favoriteRecyclerview.adapter = adapter
                        binding.progressBar.visibility = View.GONE
                        toast(requireContext(), "updated")
                    }
                }
                LOADING -> binding.progressBar.visibility = View.VISIBLE
                ERROR -> binding.progressBar.visibility = View.GONE
            }
            binding.swipeResfresLayout.isRefreshing = false
        })

        viewModel.movie.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SUCCESS -> {
                    it.data?.let {
                        binding.favoriteRecyclerview.layoutManager =
                            GridLayoutManager(requireContext(), 2)

                        adapter = HomeAdapter(it as ArrayList<SearchItem>,LayoutViews.GRID_LAYOUT) {
                            searchItemDetail = DetailFragment()
                            val bundle = Bundle()
                            bundle.putString("movieId", it.imdbID)
                            searchItemDetail!!.arguments = bundle
                            searchItemDetail!!.show(parentFragmentManager, "Bottom Sheet")
                        }
                        binding.favoriteRecyclerview.adapter = adapter
                        binding.progressBar.visibility = View.GONE
                        toast(requireContext(), "updated")
                    }
                }
                LOADING -> binding.progressBar.visibility = View.VISIBLE
                ERROR -> binding.progressBar.visibility = View.GONE
            }
            binding.swipeResfresLayout.isRefreshing = false
        })
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        val mSearchMenuItem = menu.findItem(R.id.action_search)
        mSearchMenuItem.actionView as SearchView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)

        val myActionMenuItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView = myActionMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    viewModel.getMovieByTitle(newText)
                   // searchString = newText
                }
                println(newText)
                return false
            }
        })
    }
}