package com.resurrection.movies.data.remote

import com.resurrection.movies.data.model.MovieDetails
import com.resurrection.movies.data.model.SearchResults
import retrofit2.http.GET
import retrofit2.http.Query

const val OMBD_API_KEY = "a2dd9d18"

interface InvioApiService {
/*    @GET("?apikey=a2dd9d18")
    suspend fun getMovieById() :MovieModel*/
/*
    @GET("?i=tt3896198&apikey=a2dd9d18")
*/
/*
@GET("?i={movieId}")
fun getMovieById(@Query("i") movieId: String, @Query("apikey") ombd_api_key: String = OMBD_API_KEY): MovieModel
*/

/*    @GET("?apikey=a2dd9d18")
    suspend fun getMovieById(@Query("s")searchString: String): Search*/

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
