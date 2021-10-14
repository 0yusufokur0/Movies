package com.resurrection.movies.data.remote

import com.resurrection.movies.data.model.MovieDetails
import com.resurrection.movies.data.model.SearchResults
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "a2dd9d18"

interface InvioApiService {

    @GET("?type=movie")
    suspend fun getMovieById(
        @Query(value = "s") searchTitle: String, @Query(value = "apiKey") apiKey: String, @Query(
            value = "page"
        ) pageIndex: Int
    ): SearchResults

    @GET("?plot=full")
    suspend fun getMovieDetail(
        @Query(value = "i") imdbId : String,
        @Query(value = "apiKey") apiKey: String
    ) : MovieDetails
}
