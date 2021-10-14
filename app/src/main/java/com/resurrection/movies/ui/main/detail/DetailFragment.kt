package com.resurrection.movies.ui.main.detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.resurrection.movies.R
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
        val data = arguments?.getString("cryptoId")
        data?.let {
            viewModel.getMovieDetail(data)
        }

        binding.favoriteImageView.setOnClickListener {
            // add room data base
        }

        viewModel.movieDetail.observe(viewLifecycleOwner, Observer {
            binding.movieDetail = it

        })
    }

}