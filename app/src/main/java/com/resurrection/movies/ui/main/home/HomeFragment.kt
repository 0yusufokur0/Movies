package com.resurrection.movies.ui.main.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.resurrection.movies.BR
import com.resurrection.movies.R
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.databinding.FragmentHomeBinding
import com.resurrection.movies.databinding.MovieGridItemBinding
import com.resurrection.movies.ui.base.BaseFragment
import com.resurrection.movies.ui.main.MainActivity
import com.resurrection.movies.ui.main.detail.DetailFragment
import com.resurrection.movies.ui.main.util_adapters.MovieItemAdapter
import com.resurrection.movies.util.LayoutViews
import com.resurrection.movies.util.Status.ERROR
import com.resurrection.movies.util.Status.SUCCESS
import com.resurrection.movies.util.isNetworkAvailable
import com.resurrection.movies.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    val viewModel: HomeViewModel by viewModels()
    private var searchItemDetail: DetailFragment? = null
    private var adapter: MovieItemAdapter<SearchItem, MovieGridItemBinding>? = null
    private var toast: Toast? = null
    private var sortAlertDialog: AlertDialog? = null
    var changeLayoutAlertDialog: AlertDialog? = null
    var currentLayoutView: LayoutViews = LayoutViews.GRID_LAYOUT
    private var tempList: ArrayList<SearchItem>? = ArrayList()

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun init(savedInstanceState: Bundle?) {


        setupBaseFun()

        binding.swipeResfresLayout.setOnRefreshListener { refresh(tempList) }

        binding.homeRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 10 && (requireActivity() as MainActivity).getBottomNav().isShown) {
                    (requireActivity() as MainActivity).getBottomNav().visibility = View.GONE
                } else if (dy < 0) {
                    (requireActivity() as MainActivity).getBottomNav().visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupBaseFun() {
        setHasOptionsMenu(true)
        (requireActivity() as MainActivity).setTextChangedFun { viewModel.getMovie(it) }
        setAlertDialogs()
        setViewModelsObserve()
        viewModel.getMovie("Turkey")
        toast = toast(requireContext(), "movie not found")
        toast?.cancel()
    }

    private fun setViewModelsObserve() {
        viewModel.movie.observe(viewLifecycleOwner, {
            when (it?.status) {
                SUCCESS -> {
                    binding.progressBar.visibility = View.VISIBLE
                    it.let { searchResult ->
                        searchResult.data?.search?.let {
                            refresh(it as ArrayList<SearchItem>)
                        }
                            ?: run {
                                toast?.show()
                                binding.homeRecyclerview.adapter = null
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

    private fun setAlertDialogs() {
        sortAlertDialog = (requireActivity() as MainActivity).setSortAlertDialog(
            { refresh(tempList) },
            { adapter?.sortAToZ() },
            { adapter?.sortZToA() },
            { adapter?.sortOldToNew() },
            { adapter?.sortNewToOld() }
        )
        changeLayoutAlertDialog = (requireActivity() as MainActivity)
            .setRecyclerViewLayoutAlertDialog(
                { currentLayoutView = LayoutViews.GRID_LAYOUT
                    refresh(tempList)
                }, {
                    currentLayoutView = LayoutViews.LIST_LAYOUT
                    refresh(tempList)
                })

        (requireActivity() as MainActivity).getAlertDialogs(
            sortAlertDialog,
            changeLayoutAlertDialog
        )
    }

    private fun refresh(it: ArrayList<SearchItem>?) {
        tempList = it
        toast?.cancel()
        when (currentLayoutView) {
            LayoutViews.GRID_LAYOUT -> {
                binding.homeRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = MovieItemAdapter(
                        R.layout.movie_grid_item,
                        it!!,
                        BR.searchItem,
                        this::onAdapterClick
                    )
            }

            LayoutViews.LIST_LAYOUT -> {
                binding.homeRecyclerview.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = MovieItemAdapter(
                    R.layout.movie_row_item,
                    it!!,
                    BR.searchItem,
                    this::onAdapterClick
                )
            }
        }

        binding.homeRecyclerview.adapter = adapter
        binding.progressBar.visibility = View.GONE
        if (isNetworkAvailable(requireContext())) {
            toast(requireContext(), "updated")
        }
        binding.swipeResfresLayout.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMovie("turkey")
    }

    private fun onAdapterClick(searchItem: SearchItem) {
        searchItemDetail = DetailFragment()
        val bundle = Bundle()
        bundle.putString("movieId", searchItem.imdbID)
        searchItemDetail!!.arguments = bundle
        searchItemDetail!!.show(parentFragmentManager, "Bottom Sheet")
    }
}