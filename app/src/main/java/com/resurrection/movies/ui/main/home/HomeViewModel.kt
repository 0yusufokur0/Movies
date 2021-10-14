package com.resurrection.movies.ui.main.home

import androidx.lifecycle.MutableLiveData
import com.resurrection.movies.data.model.SearchResults
import com.resurrection.movies.data.repository.MovieRepository
import com.resurrection.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val movieRepository: MovieRepository) :
    BaseViewModel() {
    var job: Job? = null
    var movie = MutableLiveData<SearchResults>()

    fun getMovie(id: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            var temp = movieRepository.api.getMovieById(id, "a2dd9d18", 1)
            movie.postValue(temp)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job = null
    }
}
