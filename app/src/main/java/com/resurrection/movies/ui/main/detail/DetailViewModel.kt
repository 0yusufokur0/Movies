package com.resurrection.movies.ui.main.detail

import androidx.lifecycle.MutableLiveData
import com.resurrection.movies.data.model.MovieDetails
import com.resurrection.movies.data.model.SearchItem
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

    private var movieDetailJob: Job? = null
    private var saveMovieItemJob: Job? = null

    var movieDetail = MutableLiveData<MovieDetails>()

    fun getMovieDetail(id: String) {
        movieDetailJob = CoroutineScope(Dispatchers.IO).launch {
            var temp = invioRepository.api.getMovieDetail(id, "a2dd9d18")
            movieDetail.postValue(temp)
        }
    }
    fun saveMovie(searchItem: SearchItem) {
        saveMovieItemJob = CoroutineScope(Dispatchers.IO).launch {
            invioRepository.dao.insertMovie(searchItem)
        }
    }

    override fun onCleared() {
        super.onCleared()
        movieDetailJob = null
    }
}