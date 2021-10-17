package com.resurrection.movies.ui.main.favorite

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.resurrection.movies.R
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.databinding.FragmentFavoriteBinding
import com.resurrection.movies.ui.base.BaseFragment
import com.resurrection.movies.ui.main.MainActivity
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
    private var sortAlertDialog: AlertDialog? = null
    private var tempList: ArrayList<SearchItem>? = ArrayList()
    private var toast: Toast? = null
    private var searchString = ""
    var changeLayoutAlertDialog: AlertDialog? = null
    var currentLayoutView: LayoutViews = LayoutViews.GRID_LAYOUT
    var searchView:SearchView? = null
    var searchText:String = ""
    override fun getLayoutRes(): Int {
        return R.layout.fragment_favorite
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun init(savedInstanceState: Bundle?) {
/*        (requireActivity() as MainActivity).getSearchView()?.cancelPendingInputEvents()
        (requireActivity() as MainActivity).getSearchView()?.onCancelPendingInputEvents()
        (requireActivity() as MainActivity).setTextChangedFun { viewModel.getMovieByTitle(it) }*/

/*

        setAlertDialogs()


        binding.swipeResfresLayout.setOnRefreshListener { refresh() }
*/

        (requireActivity() as MainActivity).getSearchView()?.cancelPendingInputEvents()
        (requireActivity() as MainActivity).getSearchView()?.onCancelPendingInputEvents()
        (requireActivity() as MainActivity).setTextChangedFun { viewModel.getMovieByTitle(it) }
        setAlertDialogs()
        setViewModelsObserve()
        viewModel.getAllFavoriteMovies()
        toast = toast(requireContext(), "movie not found")
        toast?.cancel()

        binding.swipeResfresLayout.setOnRefreshListener { tempList?.let { refresh(it) } }

        isNetworkAvailable(requireContext())
        setViewModelsObserve()
        binding.swipeResfresLayout.setOnRefreshListener { viewModel.getAllFavoriteMovies() }
    }

    private fun setViewModelsObserve() {
        viewModel.movies.observe(viewLifecycleOwner, { it ->

            when (it.status) {
                SUCCESS -> {
                    it.data?.let { refresh(it as ArrayList<SearchItem>) }
                }
                LOADING -> binding.progressBar.visibility = View.VISIBLE
                ERROR -> binding.progressBar.visibility = View.GONE
            }
            binding.swipeResfresLayout.isRefreshing = false
        })

        viewModel.movie.observe(viewLifecycleOwner, Observer {

            when (it.status) {
                SUCCESS -> { it.data?.let {refresh(it as ArrayList<SearchItem>) } }
                LOADING -> binding.progressBar.visibility = View.VISIBLE
                ERROR -> binding.progressBar.visibility = View.GONE
            }
            binding.swipeResfresLayout.isRefreshing = false
        })
    }



    private fun setAlertDialogs(){
        sortAlertDialog = (requireActivity() as MainActivity).setSortAlertDialog(
            {/* refresh() */},
            { adapter?.sortAToZ() },
            { adapter?.sortZToA() },
            { adapter?.sortOldToNew() },
            { adapter?.sortOldToNew() })
        changeLayoutAlertDialog = (requireActivity() as MainActivity)
            .setRecyclerViewLayoutAlertDialog(
                {
                    currentLayoutView = LayoutViews.GRID_LAYOUT
                    tempList?.let { refresh(it) }

                },
                {
                    currentLayoutView = LayoutViews.LIST_LAYOUT
                    tempList?.let { refresh(it) }
                })

        (requireActivity() as MainActivity).getAlertDialogs(sortAlertDialog, changeLayoutAlertDialog)

    }




    private fun refresh(mList:ArrayList<SearchItem>) {
        tempList = mList
      /*  if (tempList != null) {*/
            setMovie(mList)
            binding.swipeResfresLayout.isRefreshing = false
     /*   } else {*/
/*            if (searchString.isNotEmpty()) {
                viewModel.getMovieByTitle(searchString)
            } else {
                viewModel.getAllFavoriteMovies()
                binding.swipeResfresLayout.isRefreshing = false
                toast?.cancel()
            }*/
       /* }*/

    }



    private fun setMovie(it: List<SearchItem>?) {
        toast?.cancel()

        println(it.toString())
        when (currentLayoutView) {
            LayoutViews.GRID_LAYOUT -> binding.favoriteRecyclerview.layoutManager =
                GridLayoutManager(requireContext(), 2)
            LayoutViews.LIST_LAYOUT -> binding.favoriteRecyclerview.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        adapter =
            it?.let { it1 ->
                HomeAdapter(
                    it1 as ArrayList<SearchItem>,
                    currentLayoutView
                ) { searchItem ->
                    searchItemDetail = DetailFragment()
                    val bundle = Bundle()
                    bundle.putString("movieId", searchItem.imdbID)
                    searchItemDetail!!.arguments = bundle
                    searchItemDetail!!.show(parentFragmentManager, "Bottom Sheet")
                }
            }
        binding.favoriteRecyclerview.adapter = adapter
        binding.progressBar.visibility = View.GONE
        if (isNetworkAvailable(requireContext())) {
            toast(requireContext(), "updated")
        }
    }
















/*    private fun setAlertDialogs(){
        println("alert tıklandı")
        sortAlertDialog = (requireActivity() as MainActivity).setSortAlertDialog(
            { refresh() },
            { adapter?.sortAToZ() },
            { adapter?.sortZToA() },
            { adapter?.sortOldToNew() },
            { adapter?.sortOldToNew() })
        changeLayoutAlertDialog = (requireActivity() as MainActivity)
            .setRecyclerViewLayoutAlertDialog(
                {
                    currentLayoutView = LayoutViews.GRID_LAYOUT
                    refresh()

                },
                {
                    currentLayoutView = LayoutViews.LIST_LAYOUT
                    refresh()

                })

        (requireActivity() as MainActivity).getAlertDialogs(sortAlertDialog, changeLayoutAlertDialog)

    }*/

/*
    private fun refresh() {

        if (searchResultsList == null) {
            setMovie(searchResultsList)
            binding.swipeResfresLayout.isRefreshing = false
        } else {
            if (searchString.isNotEmpty()) {
                viewModel.getMovieByTitle(searchString)
            } else {
                binding.swipeResfresLayout.isRefreshing = false
                toast?.cancel()
            }
        }
*/




/*    override fun onPrepareOptionsMenu(menu: Menu) {
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
    }*/
/*}
    fun setMovie(it: List<SearchItem>?) {
        toast?.cancel()
        searchResultsList?.clear()
        searchResultsList?.addAll(it!!)

        when (currentLayoutView) {
            LayoutViews.GRID_LAYOUT -> binding.favoriteRecyclerview.layoutManager =
                GridLayoutManager(requireContext(), 2)
            LayoutViews.LIST_LAYOUT -> binding.favoriteRecyclerview.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        adapter =
            searchResultsList?.let { it1 ->
                HomeAdapter(
                    it1,
                    currentLayoutView
                ) { searchItem ->
                    searchItemDetail = DetailFragment()
                    val bundle = Bundle()
                    bundle.putString("movieId", searchItem.imdbID)
                    searchItemDetail!!.arguments = bundle
                    searchItemDetail!!.show(parentFragmentManager, "Bottom Sheet")
                }
            }
        binding.favoriteRecyclerview.adapter = adapter
        binding.progressBar.visibility = View.GONE
        if (isNetworkAvailable(requireContext())) {
            toast(requireContext(), "updated")
        }
    }*/
}