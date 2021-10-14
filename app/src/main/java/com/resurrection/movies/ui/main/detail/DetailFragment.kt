package com.resurrection.movies.ui.main.detail

import android.graphics.Color.green
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.resurrection.movies.R
import com.resurrection.movies.data.model.MovieDetails
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.databinding.FragmentDetailBinding
import com.resurrection.movies.ui.base.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseBottomSheetFragment<FragmentDetailBinding>() {
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)

    }
    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail
    }
    override fun init(savedInstanceState: Bundle?) {
        binding.favoriteImageView.changeIconColor(false)
        val data = arguments?.getString("cryptoId")

        data?.let {
            viewModel.getMovieDetail(data)
            viewModel.getMovieFavoriState(data)
        }

        binding.favoriteImageView.setOnClickListener {
            // add room database
            binding.favoriteImageView.changeIconColor(true)
            var movieDetail:MovieDetails = binding.movieDetail as MovieDetails
            viewModel.saveMovie(SearchItem(movieDetail.imdbID,movieDetail.type,movieDetail.year,movieDetail.poster,movieDetail.title))
        }

        viewModel.movieDetail.observe(viewLifecycleOwner, Observer { binding.movieDetail = it })
        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.favoriteImageView.changeIconColor(true)
            }else{
                binding.favoriteImageView.changeIconColor(false)
            }
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