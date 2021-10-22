package com.resurrection.movies.ui.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.data.repository.MovieRepository
import com.resurrection.movies.ui.base.BaseViewModel
import com.resurrection.movies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    BaseViewModel() {
    private val _movie = MutableLiveData<Resource<List<SearchItem>>>()
    private val _movies = MutableLiveData<Resource<List<SearchItem>>>()
    val movie: LiveData<Resource<List<SearchItem>>> = _movie
    val movies: LiveData<Resource<List<SearchItem>>> = _movies

    fun getAllFavoriteMovies()  = viewModelScope.launch{
            movieRepository.getFavoriteMovies()
                .onStart { _movies.postValue(Resource.Loading()) }
                .catch { message -> _movies.postValue(Resource.Error(message)) }
                .collect { it.let { _movies.postValue(it) } }
    }

    fun getMovieByTitle(title: String)  = viewModelScope.launch{
            movieRepository.getMovieByTitle(title)
                .onStart { _movie.postValue(Resource.Loading()) }
                .catch { message -> _movie.postValue(Resource.Error(message)) }
                .collect { it.let { _movie.postValue(it) } }
    }

}
