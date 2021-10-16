package com.resurrection.movies.ui.main.home

import androidx.lifecycle.MutableLiveData
import com.resurrection.movies.data.model.SearchResults
import com.resurrection.movies.data.repository.MovieRepository
import com.resurrection.movies.ui.base.BaseViewModel
import com.resurrection.movies.util.Resource
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
class HomeViewModel @Inject constructor(val movieRepository: MovieRepository) :
    BaseViewModel() {
    var job: Job? = null

    private val _movie = MutableLiveData<Resource<SearchResults>>()
    val movie: MutableLiveData<Resource<SearchResults>> = _movie

    fun getMovie(id: String) {
        job = CoroutineScope(Dispatchers.IO).launch {

            movieRepository.getMovieById(id, "a2dd9d18", 1)
                .onStart { _movie.postValue(Resource.Loading()) }
                .catch { message -> _movie.postValue(Resource.Error(message)) }
                .collect { _movie.postValue(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job = null
    }
}
