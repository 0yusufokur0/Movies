package com.resurrection.movies.ui.main.detail

import androidx.lifecycle.MutableLiveData
import com.resurrection.movies.data.model.MovieDetails
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.data.repository.MovieRepository
import com.resurrection.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class DetailViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    BaseViewModel() {

    private var movieDetailJob: Job? = null
    private var saveMovieItemJob: Job? = null
    private var movieFavoriteStateItemJob: Job? = null
    private var removeMovieJob:Job? = null
    var isFavorite = MutableLiveData<Boolean>()
    var movieDetail = MutableLiveData<MovieDetails>()

    fun getMovieDetail(id: String) {
        movieDetailJob = CoroutineScope(Dispatchers.IO).launch {
/*
            val temp = movieRepository.api.getMovieDetail(id, "a2dd9d18")
*/
/*
            movieDetail.postValue(temp)
*/
        }
    }

    fun saveMovie(searchItem: SearchItem) {
        saveMovieItemJob = CoroutineScope(Dispatchers.IO).launch {
/*
            movieRepository.dao.insertMovie(searchItem)
*/
        }
    }

    fun getMovieFavoriteState(id: String) {
        movieFavoriteStateItemJob = CoroutineScope(Dispatchers.IO).launch {
 /*           val temp = movieRepository.dao.getMovieById(id)
            temp?.let {
                println(temp.imdbID)
                isFavorite.postValue(true)
            } ?:run {
                isFavorite.postValue(false)
            }
*/
        }
    }
    fun removeMovie(searchItem: SearchItem){
        removeMovieJob = CoroutineScope(Dispatchers.IO).launch {
/*
            movieRepository.dao.removeMovie(searchItem)
*/
        }
    }

    override fun onCleared() {
        super.onCleared()
        movieDetailJob = null
    }
}