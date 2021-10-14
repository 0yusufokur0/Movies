package com.resurrection.movies.ui.main.detail

import androidx.lifecycle.MutableLiveData
import com.resurrection.movies.data.model.MovieDetails
import com.resurrection.movies.data.repository.InvioRepository
import com.resurrection.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class DetailViewModel  @Inject constructor(val invioRepository: InvioRepository) :
    BaseViewModel() {

    var job: Job? = null
    var movieDetail = MutableLiveData<MovieDetails>()

    fun getMovieDetail(id: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            var temp = invioRepository.api.getMovieDetail(id, "a2dd9d18")
            movieDetail.postValue(temp)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job = null
    }
}