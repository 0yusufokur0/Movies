package com.resurrection.movies.ui.main.home

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
import com.resurrection.movies.databinding.FragmentHomeBinding
import com.resurrection.movies.ui.base.BaseFragment
import com.resurrection.movies.ui.main.MainActivity
import com.resurrection.movies.ui.main.detail.DetailFragment
import com.resurrection.movies.util.LayoutViews
import com.resurrection.movies.util.Status.ERROR
import com.resurrection.movies.util.Status.SUCCESS
import com.resurrection.movies.util.isNetworkAvailable
import com.resurrection.movies.util.toast
import dagger.hilt.android.AndroidEntryPoint

import androidx.navigation.NavController
import androidx.navigation.Navigation


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    val viewModel: HomeViewModel by viewModels()
    private var searchResultsList: ArrayList<SearchItem>? = ArrayList()
    private var searchItemDetail: DetailFragment? = null
    private var adapter: HomeAdapter? = null
    private var toast: Toast? = null
    private var searchString = ""
    private var sortAlertDialog: AlertDialog? = null
    var changeLayoutAlertDialog: AlertDialog? = null
    var currentLayoutView: LayoutViews = LayoutViews.GRID_LAYOUT
    var searchView:SearchView? = null
    var searchText:String = ""
    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }


    override fun init(savedInstanceState: Bundle?) {
        (requireActivity() as MainActivity).getSearchView()?.cancelPendingInputEvents()
        (requireActivity() as MainActivity).getSearchView()?.onCancelPendingInputEvents()
      (requireActivity() as MainActivity).setTextChangedFun { viewModel.getMovie(it) }
        setAlertDialogs()
        setViewModelsObserve()
        viewModel.getMovie("Turkey")
        toast = toast(requireContext(), "movie not found")
        toast?.cancel()

        binding.swipeResfresLayout.setOnRefreshListener { refresh() }
    }

    private fun setAlertDialogs(){
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

    }

    private fun setViewModelsObserve() {
        viewModel.movie.observe(viewLifecycleOwner, Observer {
            when (it?.status) {
                SUCCESS -> {
                    binding.progressBar.visibility = View.VISIBLE
                    it.let { searchResult ->
                        searchResult.data?.search?.let { searchList ->
                            setMovie(searchList)
                        } ?: run {
                            toast?.show()
                            binding.homeRecyclerview.adapter =
                                HomeAdapter(ArrayList(), currentLayoutView) {}
                            binding.progressBar.visibility = View.GONE
                            isNetworkAvailable(requireContext())
                        }

                    }
                    binding.swipeResfresLayout.isRefreshing = false
                }
                ERROR -> toast(requireContext(), "could not be load")
            }
        })
    }

    private fun setMovie(it: List<SearchItem>?) {
        toast?.cancel()
        searchResultsList?.clear()
        searchResultsList?.addAll(it!!)

        when (currentLayoutView) {
            LayoutViews.GRID_LAYOUT -> binding.homeRecyclerview.layoutManager =
                GridLayoutManager(requireContext(), 2)
            LayoutViews.LIST_LAYOUT -> binding.homeRecyclerview.layoutManager =
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
        binding.homeRecyclerview.adapter = adapter
        binding.progressBar.visibility = View.GONE
        if (isNetworkAvailable(requireContext())) {
            toast(requireContext(), "updated")
        }
    }

    private fun refresh() {

        if (searchResultsList == null) {
            setMovie(searchResultsList)
            binding.swipeResfresLayout.isRefreshing = false
        } else {
            if (searchString.isNotEmpty()) {
                viewModel.getMovie(searchString)
            } else {
                viewModel.getMovie("Turkey")
                binding.swipeResfresLayout.isRefreshing = false
                toast?.cancel()
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }
/*
    override fun onPrepareOptionsMenu(menu: Menu) {
        val mSearchMenuItem = menu.findItem(R.id.action_search)
        mSearchMenuItem.actionView as SearchView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)

        val myActionMenuItem: MenuItem = menu.findItem(R.id.action_search)
        searchView = myActionMenuItem.actionView as SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    viewModel.getMovie(newText)
                    searchString = newText
                }
                println(newText)
                return false
            }
        })


    }

    */


    override fun onResume() {
        super.onResume()

    }
}