package com.resurrection.movies.ui.main.detail

import androidx.lifecycle.MutableLiveData

import com.resurrection.movies.data.model.MovieDetails
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.data.repository.MovieRepository
import com.resurrection.movies.ui.base.BaseViewModel
import com.resurrection.movies.util.Resource
import com.resurrection.movies.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class DetailViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    BaseViewModel() {

    private var movieDetailJob: Job? = null
    private var insertMovieItemJob: Job? = null
    private var movieFavoriteStateItemJob: Job? = null
    private var removeMovieJob: Job? = null
    private var _isFavorite = MutableLiveData<Resource<Boolean>>()
    private val _movieDetail = MutableLiveData<Resource<MovieDetails>>()
    private val _insertMovie = MutableLiveData<Resource<Status>>()
    private val _isRemoved = MutableLiveData<Resource<Boolean>>()
    val insertMovie: MutableLiveData<Resource<Status>> = _insertMovie
    val isRemoved: MutableLiveData<Resource<Boolean>> = _isRemoved
    val movieDetail: MutableLiveData<Resource<MovieDetails>> = _movieDetail
    val isFavorite: MutableLiveData<Resource<Boolean>> = _isFavorite


    fun getMovieDetail(id: String) {
        movieDetailJob = CoroutineScope(Dispatchers.IO).launch {
            movieRepository.getMovieDetail(id, "a2dd9d18")
                .onStart { _movieDetail.postValue(Resource.Loading()) }
                .catch { message -> _movieDetail.postValue(Resource.Error(message)) }
                .collect { _movieDetail.postValue(Resource.Success(it.data)) }
        }
    }

    fun insertMovie(searchItem: SearchItem) {
        insertMovieItemJob = CoroutineScope(Dispatchers.IO).launch {
            movieRepository.insertMovie(searchItem)
                .onStart { _insertMovie.postValue(Resource.Loading()) }
                .catch { message -> _insertMovie.postValue(Resource.Error(message)) }
                .collect { _insertMovie.postValue(Resource.Success(null)) }
        }
    }

    fun getMovieFavoriteState(id: String) {
        movieFavoriteStateItemJob = CoroutineScope(Dispatchers.IO).launch {
            movieRepository.getMovieById(id)
                .onStart { _isFavorite.postValue(Resource.Loading()) }
                .catch { message -> _isFavorite.postValue(Resource.Error(message)) }
                .collect {
                    it.data?.let { _isFavorite.postValue(Resource.Success(true)) }
                        ?: run { _isFavorite.postValue(Resource.Success(false)) }
                }
        }
    }

    fun removeMovie(searchItem: SearchItem) {
        removeMovieJob = CoroutineScope(Dispatchers.IO).launch {
            movieRepository.removeMovie(searchItem)
                .onStart { _isRemoved.postValue(Resource.Loading()) }
                .catch { message -> _isRemoved.postValue(Resource.Error(message)) }
                .collect {
                    it.data?.let { _isRemoved.postValue(Resource.Success(true)) }
                        ?: run { _isRemoved.postValue(Resource.Success(false)) }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        movieDetailJob = null
    }
}