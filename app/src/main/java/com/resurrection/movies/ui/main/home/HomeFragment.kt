package com.resurrection.movies.ui.main.home

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.resurrection.movies.R
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.databinding.FragmentHomeBinding
import com.resurrection.movies.ui.base.BaseFragment
import com.resurrection.movies.ui.main.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    val viewModel: HomeViewModel by viewModels()
    private var searchResultsList: ArrayList<SearchItem> = ArrayList()
    private var searchItemDetail: DetailFragment? = null
    var adapter: HomeAdapter? = null
    var toast:Toast? = null
    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun init(savedInstanceState: Bundle?) {
        viewModel.getMovie("Turkey")
        viewModel.movie.observe(this, Observer {
            binding.progressBar.visibility = View.VISIBLE
            it?.let {
                it.search?.let {
                    toast?.cancel()
                    searchResultsList.addAll(it)
                    /*         val llm = LinearLayoutManager(requireContext())
                             llm.orientation = LinearLayoutManager.VERTICAL
                             binding.homeRecyclerview.layoutManager = llm*/

                    binding.homeRecyclerview.layoutManager = GridLayoutManager(requireContext(),2)

                    adapter = HomeAdapter(it as ArrayList<SearchItem>) {
                        searchItemDetail = DetailFragment()
                        val bundle = Bundle()
                        bundle.putString("cryptoId", it.imdbID)
                        searchItemDetail!!.arguments = bundle
                        searchItemDetail!!.show(parentFragmentManager, "Bottom Sheet")
                    }
                    binding.homeRecyclerview.adapter = adapter
                    binding.progressBar.visibility = View.GONE
                }

            } ?: run {

                binding.homeRecyclerview.adapter = HomeAdapter(ArrayList<SearchItem>(),{})
                binding.progressBar.visibility = View.GONE
                toast = Toast.makeText(requireContext(), "no found result", Toast.LENGTH_SHORT)
                toast?.show()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true);
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        val mSearchMenuItem = menu.findItem(R.id.action_search)
        val searchView = mSearchMenuItem.actionView as SearchView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)

        val myActionMenuItem: MenuItem = menu.findItem(R.id.action_search)
        var searchView = myActionMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                   if (newText.length != 0) {
                    viewModel.getMovie(newText)
                }
                println(newText)
                return false
            }
        })
    }


}