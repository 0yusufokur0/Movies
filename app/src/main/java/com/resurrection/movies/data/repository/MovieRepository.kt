package com.resurrection.movies.data.repository

import com.resurrection.movies.data.model.MovieDetails
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.data.model.SearchResults
import com.resurrection.movies.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    // Network
    suspend fun getMovieById(id: String, page: Int): Flow<Resource<SearchResults>>
    suspend fun getMovieDetail(imdbId: String): Flow<Resource<MovieDetails>>

    // Database
    suspend fun insertMovie(movie: SearchItem): Flow<Resource<Unit>>
    suspend fun removeMovie(movie: SearchItem): Flow<Resource<Unit>>
    suspend fun getFavoriteMovies(): Flow<Resource<List<SearchItem>>>
    suspend fun getMovieById(imdbID: String): Flow<Resource<SearchItem>>
    suspend fun getMovieByTitle(title: String): Flow<Resource<List<SearchItem>>>
}

