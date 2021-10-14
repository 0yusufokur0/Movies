package com.resurrection.movies.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.resurrection.movies.data.model.SearchResults
import com.resurrection.movies.data.repository.InvioRepository
import com.resurrection.movies.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(val invioRepository: InvioRepository) :
    BaseViewModel() {
    var job: Job? = null
    var movie = MutableLiveData<SearchResults>()

    fun getMovie(id: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            var temp = invioRepository.api.getMovieById(id, "a2dd9d18", 1)
            movie.postValue(temp)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job = null
    }
}
