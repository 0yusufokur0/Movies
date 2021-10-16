package com.resurrection.movies.data.remote

import com.resurrection.movies.data.model.MovieDetails
import com.resurrection.movies.data.model.SearchResults
import com.resurrection.movies.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("?type=movie")
    suspend fun getMovieById(
        @Query(value = "s") searchTitle: String,
        @Query(value = "page") pageIndex: Int,
        @Query(value = "apiKey") apiKey: String = API_KEY,
    ): Response<SearchResults>

    @GET("?plot=full")
    suspend fun getMovieDetail(
        @Query(value = "i") imdbId: String,
        @Query(value = "apiKey") apiKey: String = API_KEY
    ): Response<MovieDetails>
}
