package com.resurrection.movies.data.repository

import com.resurrection.movies.data.db.dao.MovieDao
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.data.model.SearchResults
import com.resurrection.movies.data.remote.InvioApiService
import com.resurrection.movies.util.Resource
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MovieRepository {
    suspend fun getMovieById(id:String, apiKey:String, page:Int): Flow<Resource<SearchResults>>

}


/*
class MovieRepository @Inject constructor(private val movieDao : MovieDao, private val invioApiService: InvioApiService) {
    val api = invioApiService
    val dao = movieDao
}*/
