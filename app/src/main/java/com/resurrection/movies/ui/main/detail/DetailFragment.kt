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
import com.resurrection.movies.util.Status
import com.resurrection.movies.util.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseBottomSheetFragment<FragmentDetailBinding>() {
    private val viewModel: DetailViewModel by viewModels()
    private var favoriteState: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

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
    private fun setFavoriteAction(){
        val movieDetail: MovieDetails = binding.movieDetail as MovieDetails
        val currentSearchItem =
            SearchItem(
                movieDetail.imdbID,
                movieDetail.type,
                movieDetail.year,
                movieDetail.poster,
                movieDetail.title
            )

        if (favoriteState) {
            viewModel.removeMovie(currentSearchItem)
            binding.favoriteImageView.changeIconColor(false)
            favoriteState = false
        } else {
            binding.favoriteImageView.changeIconColor(true)
            binding.movieDetail as MovieDetails
            viewModel.saveMovie(currentSearchItem)
            favoriteState = true
        }
    }

    private fun setViewModelsObserve(){
        viewModel.movieDetail.observe(viewLifecycleOwner, {
            when(it.status){
                Status.SUCCESS -> {
                    it.data?.let {
                        binding.movieDetail = it
                    }
                }
                Status.LOADING -> {}
                Status.ERROR -> {}
            }
        })
        viewModel.isFavorite.observe(viewLifecycleOwner, {
            favoriteState = it
            binding.favoriteImageView.changeIconColor(favoriteState)
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