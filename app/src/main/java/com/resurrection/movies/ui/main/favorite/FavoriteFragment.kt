package com.resurrection.movies.ui.main.favorite

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.resurrection.movies.BR
import com.resurrection.movies.R
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.databinding.FragmentFavoriteBinding
import com.resurrection.movies.databinding.MovieGridItemBinding
import com.resurrection.movies.ui.base.BaseFragment
import com.resurrection.movies.ui.main.MainActivity
import com.resurrection.movies.ui.main.detail.DetailFragment
import com.resurrection.movies.ui.main.util_adapters.MovieItemAdapter
import com.resurrection.movies.util.LayoutViews
import com.resurrection.movies.util.Status.*
import com.resurrection.movies.util.isNetworkAvailable
import com.resurrection.movies.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(){
    private val viewModel: FavoriteViewModel by viewModels()
    private var adapter: MovieItemAdapter<SearchItem, MovieGridItemBinding>? = null
    private var sortAlertDialog: AlertDialog? = null
    private var tempList: ArrayList<SearchItem>? = ArrayList()
    private var toast: Toast? = null
    var changeLayoutAlertDialog: AlertDialog? = null
    var currentLayoutView: LayoutViews = LayoutViews.GRID_LAYOUT

    override fun getLayoutRes(): Int = R.layout.fragment_favorite

    override fun init(savedInstanceState: Bundle?) {
        setupBaseFun()
        binding.swipeResfresLayout.setOnRefreshListener {
            tempList?.let { refresh(it) } ?: run { viewModel.getAllFavoriteMovies() }
        }
    }

    private fun setupBaseFun() {
        setHasOptionsMenu(true)
        (requireActivity() as MainActivity).setTextChangedFun { viewModel.getMovieByTitle(it) }
        setAlertDialogs()
        setViewModelsObserve()
        viewModel.getAllFavoriteMovies()
        toast = toast(requireContext(), "movie not found")
        toast?.cancel()
        isNetworkAvailable(requireContext())
    }

    private fun setViewModelsObserve() {
        viewModel.movies.observe(viewLifecycleOwner, { it ->
            when (it.status) {
                SUCCESS -> it.data?.let { refresh(it as ArrayList<SearchItem>) }
                LOADING -> binding.progressBar.visibility = View.VISIBLE
                ERROR -> binding.progressBar.visibility = View.GONE
            }
            binding.swipeResfresLayout.isRefreshing = false
        })

        viewModel.movie.observe(viewLifecycleOwner, {
            when (it.status) {
                SUCCESS -> it.data?.let { refresh(it as ArrayList<SearchItem>) }
                LOADING -> binding.progressBar.visibility = View.VISIBLE
                ERROR -> binding.progressBar.visibility = View.GONE
            }
            binding.swipeResfresLayout.isRefreshing = false
        })
    }


    private fun setAlertDialogs() {
        sortAlertDialog = (requireActivity() as MainActivity).setSortAlertDialog(
            {refresh(tempList)},
            { adapter?.sortAToZ() },
            { adapter?.sortZToA() },
            { adapter?.sortOldToNew() },
            { adapter?.sortNewToOld() })
        changeLayoutAlertDialog = (requireActivity() as MainActivity)
            .setRecyclerViewLayoutAlertDialog(
                { currentLayoutView = LayoutViews.GRID_LAYOUT
                    refresh(tempList)
                }, {
                   currentLayoutView = LayoutViews.LIST_LAYOUT
                   refresh(tempList) })

        (requireActivity() as MainActivity).getAlertDialogs(
            sortAlertDialog,
            changeLayoutAlertDialog
        )
    }

    private fun refresh(it: ArrayList<SearchItem>?) {
        tempList = it
        binding.swipeResfresLayout.isRefreshing = false
        toast?.cancel()
        
        when (currentLayoutView) {
            LayoutViews.GRID_LAYOUT -> {
                binding.favoriteRecyclerview.layoutManager =
                    GridLayoutManager(requireContext(), 2)
                binding.favoriteRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = MovieItemAdapter(
                    R.layout.movie_grid_item,
                    it!!,
                    BR.searchItem,
                    this::onAdapterClick
                )
            }
            LayoutViews.LIST_LAYOUT -> {
                binding.favoriteRecyclerview.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = MovieItemAdapter(
                    R.layout.movie_row_item,
                    it!!,
                    BR.searchItem,
                    this::onAdapterClick
                )
            }
        }

        binding.favoriteRecyclerview.adapter = adapter
        binding.progressBar.visibility = View.GONE
        if (isNetworkAvailable(requireContext())) {
            toast(requireContext(), "updated")
        }
        binding.swipeResfresLayout.isRefreshing = false

    }
    fun onAdapterClick(searchItem: SearchItem){
        val searchItemDetail = DetailFragment()
        val bundle = Bundle()
        bundle.putString("movieId", searchItem.imdbID)
        searchItemDetail.arguments = bundle
        searchItemDetail.show(parentFragmentManager, "Bottom Sheet")
    }
}