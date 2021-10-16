package com.resurrection.movies.ui.main.detail

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.resurrection.movies.R

import com.resurrection.movies.data.model.MovieDetails
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.data.repository.MovieRepository
import com.resurrection.movies.ui.base.BaseViewModel
import com.resurrection.movies.util.Resource
import com.resurrection.movies.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.coroutineContext
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class DetailViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    BaseViewModel() {

    private var movieDetailJob: Job? = null
    private var saveMovieItemJob: Job? = null
    private var movieFavoriteStateItemJob: Job? = null
    private var removeMovieJob: Job? = null
    var isFavorite = MutableLiveData<Boolean>()
    private val _movieDetail = MutableLiveData<Resource<MovieDetails>>()
    var movieDetail: MutableLiveData<Resource<MovieDetails>> = _movieDetail

    fun getMovieDetail(id: String) {
        movieDetailJob = CoroutineScope(Dispatchers.IO).launch {

            movieRepository.getMovieDetail(id, "a2dd9d18")
                .onStart {

                }.catch {

                }.collect {
                    _movieDetail.postValue(it)
                }
        }
    }

    fun saveMovie(searchItem: SearchItem) {
        saveMovieItemJob = CoroutineScope(Dispatchers.IO).launch {
            movieRepository.insertMovie(searchItem)
                .onStart {
/*
                    _result.value = Result(loading = R.string.loading)
*/
                }.catch {

                }.collect {

                }
        }
    }

    fun getMovieFavoriteState(id: String) {
        movieFavoriteStateItemJob = CoroutineScope(Dispatchers.IO).launch {
            movieRepository.getMovieById(id)
                .onStart {
                    // Loading
                }.catch {

                }.collect {
                    it.data?.let {
                        isFavorite.postValue(true)
                    } ?: run {
                        isFavorite.postValue(false)
                    }
                }
        }
    }

    fun removeMovie(searchItem: SearchItem) {
        removeMovieJob = CoroutineScope(Dispatchers.IO).launch {
            movieRepository.removeMovie(searchItem)
                .onStart {

                }.catch {

                }.collect {

                }

        }
    }

    override fun onCleared() {
        super.onCleared()
        movieDetailJob = null
    }
}