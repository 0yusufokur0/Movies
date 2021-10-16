package com.resurrection.movies.ui.main.home

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.resurrection.movies.R
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.databinding.FragmentHomeBinding
import com.resurrection.movies.databinding.SortDialogBinding
import com.resurrection.movies.ui.base.BaseFragment
import com.resurrection.movies.ui.main.detail.DetailFragment
import com.resurrection.movies.util.Status.ERROR
import com.resurrection.movies.util.Status.SUCCESS
import com.resurrection.movies.util.isNetworkAvailable
import com.resurrection.movies.util.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    val viewModel: HomeViewModel by viewModels()
    private var searchResultsList: ArrayList<SearchItem> = ArrayList()
    private var searchItemDetail: DetailFragment? = null
    private var adapter: HomeAdapter? = null
    private var toast: Toast? = null
    private var searchString = ""
    private var alertDialog:AlertDialog? = null
    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun init(savedInstanceState: Bundle?) {
        alertDialog = setSortAlertDialog()
        setViewModelsObserve()
        viewModel.getMovie("Turkey")
        toast = toast(requireContext(), "movie not found")
        toast?.cancel()

        binding.swipeResfresLayout.setOnRefreshListener { refresh() }
    }

    private fun setViewModelsObserve() {
        viewModel.movie.observe(this, {
            when (it.status) {
                SUCCESS -> {
                    binding.progressBar.visibility = View.VISIBLE
                    it?.let { searchResult ->
                        searchResult.data?.search?.let { searchList ->
                            toast?.cancel()
                            searchResultsList.addAll(searchList)

                            binding.homeRecyclerview.layoutManager =
                                GridLayoutManager(requireContext(), 2)

                            adapter =
                                HomeAdapter(searchList as ArrayList<SearchItem>) { searchItem ->
                                    searchItemDetail = DetailFragment()
                                    val bundle = Bundle()
                                    bundle.putString("movieId", searchItem.imdbID)
                                    searchItemDetail!!.arguments = bundle
                                    searchItemDetail!!.show(parentFragmentManager, "Bottom Sheet")
                                }
                            binding.homeRecyclerview.adapter = adapter
                            binding.progressBar.visibility = View.GONE
                            toast(requireContext(), "updated")
                        } ?: run {
                            toast?.show()
                            binding.homeRecyclerview.adapter = HomeAdapter(ArrayList()) {}
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
    private fun setSortAlertDialog(): AlertDialog? {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val alertBinding: SortDialogBinding = SortDialogBinding.inflate(LayoutInflater.from(context))
        dialogBuilder.setView(alertBinding.root)

        val alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
        alertBinding.recommended.setOnClickListener { refresh() }
        alertBinding.sortAToZ.setOnClickListener { adapter?.sortAToZ() }
        alertBinding.sortZtoA.setOnClickListener { adapter?.sortZToA() }
        alertBinding.sortOldToNew.setOnClickListener { adapter?.sortOldToNew() }
        alertBinding.sortNewToOld.setOnClickListener { adapter?.sortNewToOld() }
        return alertDialog
    }
    private fun refresh(){
        if (searchString.isNotEmpty()) {
            viewModel.getMovie(searchString)
        } else {
            viewModel.getMovie("Turkey")
            toast?.cancel()
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sort -> alertDialog?.show()
        }
        return super.onOptionsItemSelected(item)

    }


}