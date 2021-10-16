package com.resurrection.movies.ui.main.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.resurrection.movies.R
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.databinding.FragmentHomeBinding
import com.resurrection.movies.ui.base.BaseFragment
import com.resurrection.movies.ui.main.detail.DetailFragment
import com.resurrection.movies.util.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    val viewModel: HomeViewModel by viewModels()
    private var searchResultsList: ArrayList<SearchItem> = ArrayList()
    private var searchItemDetail: DetailFragment? = null
    private var adapter: HomeAdapter? = null
    private var toast: Toast? = null
    private var searchString = ""
    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun init(savedInstanceState: Bundle?) {
        viewModel.getMovie("Turkey")
        setViewModelsObserve()

        binding.swipeResfresLayout.setOnRefreshListener {
            if (searchString.isNotEmpty()){
                viewModel.getMovie(searchString)
            }else{
                viewModel.getMovie("Turkey")
            }
        }
    }

    private fun setViewModelsObserve(){
        viewModel.movie.observe(this, { searchResults ->
            binding.progressBar.visibility = View.VISIBLE
            searchResults?.let { searchResult ->
                searchResult.data?.search?.let { searchList ->
                    toast?.cancel()
                    searchResultsList.addAll(searchList)

                    binding.homeRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)

                    adapter = HomeAdapter(searchList as ArrayList<SearchItem>) { searchItem ->
                        searchItemDetail = DetailFragment()
                        val bundle = Bundle()
                        bundle.putString("movieId", searchItem.imdbID)
                        searchItemDetail!!.arguments = bundle
                        searchItemDetail!!.show(parentFragmentManager, "Bottom Sheet")
                    }
                    binding.homeRecyclerview.adapter = adapter
                    binding.progressBar.visibility = View.GONE
                }

            } ?: run {

                binding.homeRecyclerview.adapter = HomeAdapter(ArrayList()) {}
                binding.progressBar.visibility = View.GONE
                isNetworkAvailable(requireContext())
                toast?.show()
            }
            binding.swipeResfresLayout.isRefreshing = false
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
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
                    viewModel.getMovie(newText)
                    searchString = newText
                }
                println(newText)
                return false
            }
        })
    }


}