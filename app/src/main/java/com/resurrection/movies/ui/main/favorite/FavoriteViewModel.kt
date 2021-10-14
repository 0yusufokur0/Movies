package com.resurrection.movies.ui.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.resurrection.movies.data.model.SearchItem
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
class FavoriteViewModel @Inject constructor(val invioRepository: InvioRepository) :
    BaseViewModel() {
    var job: Job? = null
    var movies = MutableLiveData<List<SearchItem>>()

    fun getAllFavoriteMovies() {
        job = CoroutineScope(Dispatchers.IO).launch {
            var temp  = invioRepository.dao.getCryptoFavorite()
            movies.postValue(temp)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job = null
    }
}
