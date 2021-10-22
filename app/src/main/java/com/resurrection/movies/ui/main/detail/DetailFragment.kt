package com.resurrection.movies.ui.main.detail

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.resurrection.movies.R
import com.resurrection.movies.data.model.MovieDetails
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.databinding.FragmentDetailBinding
import com.resurrection.movies.ui.base.BaseBottomSheetFragment
import com.resurrection.movies.ui.main.MainActivity
import com.resurrection.movies.util.Status.ERROR
import com.resurrection.movies.util.Status.SUCCESS
import com.resurrection.movies.util.isNetworkAvailable
import com.resurrection.movies.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseBottomSheetFragment<FragmentDetailBinding>() {
    private val viewModel: DetailViewModel by viewModels()
    private var favoriteState: Boolean? = false
    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail
    }

    override fun init(savedInstanceState: Bundle?) {
        setViewModelsObserve()
        isNetworkAvailable(requireContext())
        binding.favoriteImageView.changeIconColor(false)
        val data = arguments?.getString("movieId")

        data?.let {
            viewModel.getMovieDetail(data)
            viewModel.getMovieFavoriteState(data)
        }
        binding.favoriteImageView.setOnClickListener { setFavoriteAction() }
    }

    private fun setFavoriteAction() {
        val movieDetail: MovieDetails = binding.movieDetail as MovieDetails
        val currentSearchItem =
            SearchItem(
                movieDetail.imdbID,
                movieDetail.type,
                movieDetail.year,
                movieDetail.poster,
                movieDetail.title
            )

        favoriteState?.let {
            favoriteState = if (favoriteState!!) {
                viewModel.removeMovie(currentSearchItem)
                binding.favoriteImageView.changeIconColor(false)
                false
            } else {
                binding.favoriteImageView.changeIconColor(true)
                binding.movieDetail as MovieDetails
                viewModel.insertMovie(currentSearchItem)
                true
            }
        }
    }

    private fun setViewModelsObserve() {
        viewModel.movieDetail.observe(viewLifecycleOwner, {
            when (it.status) {
                SUCCESS -> it.data?.let { binding.movieDetail = it }
                ERROR -> toast(requireContext(), "could not be load")
            }
        })

        viewModel.insertMovie.observe(viewLifecycleOwner, {
            when (it.status) {
                SUCCESS -> toast(requireContext(), "added favorite")
                ERROR -> toast(requireContext(), "could not be added to favorites")
            }
        })
        viewModel.isRemoved.observe(viewLifecycleOwner,{
            when(it.status){
                SUCCESS -> (requireActivity() as MainActivity).refreshDataWhenRemovedMovie()
                ERROR -> toast(requireContext(), "could not be removed")
            }
        })
        viewModel.isFavorite.observe(viewLifecycleOwner, {
            when (it.status) {SUCCESS -> favoriteState = it.data  }
            favoriteState?.let { binding.favoriteImageView.changeIconColor(favoriteState!!) }
        })
    }

    private infix fun ImageView.changeIconColor(isFavourite: Boolean) {
        val color = if (isFavourite) R.color.green else R.color.red
        this.colorFilter = PorterDuffColorFilter(
            ContextCompat.getColor(requireContext(), color),
            PorterDuff.Mode.SRC_IN
        )
    }
}