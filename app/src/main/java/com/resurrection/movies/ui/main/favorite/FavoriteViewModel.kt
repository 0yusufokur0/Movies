package com.resurrection.movies.ui.main.favorite

import androidx.lifecycle.MutableLiveData
import com.resurrection.movies.data.model.SearchItem
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
class FavoriteViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    BaseViewModel() {
    private var job: Job? = null
    var movies = MutableLiveData<Resource<List<SearchItem>>>()

    fun getAllFavoriteMovies() {
        job = CoroutineScope(Dispatchers.IO).launch {
            movieRepository.getFavoriteMovies()
                .onStart {

                }.catch {

                }.collect {
                    it.let {
                        movies.postValue(it)
                    }
                }

        }
    }

    override fun onCleared() {
        super.onCleared()
        job = null
    }
}
