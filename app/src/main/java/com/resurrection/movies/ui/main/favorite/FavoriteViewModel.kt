package com.resurrection.movies.ui.main.favorite

import androidx.lifecycle.MutableLiveData
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
class FavoriteViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    BaseViewModel() {
    private var job: Job? = null
    var movies = MutableLiveData<List<SearchItem>>()

    fun getAllFavoriteMovies() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val temp  = movieRepository.dao.getFavoriteMovies()
            movies.postValue(temp)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job = null
    }
}
