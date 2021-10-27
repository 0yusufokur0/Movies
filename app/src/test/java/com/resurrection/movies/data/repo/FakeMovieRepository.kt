package com.resurrection.movies.data.repo

import androidx.lifecycle.MutableLiveData
import com.resurrection.movies.data.model.MovieDetails
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.data.model.SearchResults
import com.resurrection.movies.data.repository.MovieRepository
import com.resurrection.movies.util.Resource
import com.resurrection.movies.util.getResourceByDatabaseRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository :MovieRepository{

    private val movies = mutableListOf<SearchItem>()
    private val moviesLiveData = MutableLiveData<List<SearchItem>>(movies)


    override suspend fun getMovieById(id: String, page: Int): Flow<Resource<SearchResults>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieById(imdbID: String): Flow<Resource<SearchItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetail(imdbId: String): Flow<Resource<MovieDetails>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMovie(movie: SearchItem): Flow<Resource<Unit>> = flow {
        emit(getResourceByDatabaseRequest { addMovies(movie)})
    }

    override suspend fun removeMovie(movie: SearchItem): Flow<Resource<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteMovies(): Flow<Resource<List<SearchItem>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieByTitle(title: String): Flow<Resource<List<SearchItem>>> {
        TODO("Not yet implemented")
    }

    private fun refresh(){
        moviesLiveData.postValue(movies)
    }

    suspend fun addMovies(movie: SearchItem){
        movies.add(movie)
        refresh()
    }


}