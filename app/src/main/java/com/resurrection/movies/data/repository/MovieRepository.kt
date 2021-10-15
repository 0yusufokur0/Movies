package com.resurrection.movies.data.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.resurrection.movies.data.db.dao.MovieDao
import com.resurrection.movies.data.model.MovieDetails
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.data.model.SearchResults
import com.resurrection.movies.data.remote.InvioApiService
import com.resurrection.movies.util.Resource
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query
import javax.inject.Inject

interface MovieRepository {
    suspend fun getMovieById(id:String, apiKey:String, page:Int): Flow<Resource<SearchResults>>
    suspend fun getMovieDetail(imdbId : String, apiKey: String): Flow<Resource<MovieDetails>>

    suspend fun insertMovie(movie: SearchItem): Flow<Resource<Unit>>
    suspend fun removeMovie(movie: SearchItem): Flow<Resource<Unit>>
    suspend fun getFavoriteMovies(): Flow<Resource<List<SearchItem>>>
    suspend fun getMovieById(imdbID: String): Flow<Resource<SearchItem>>
}
/*
    suspend fun getCoinsByParameter(parameter: String): Flow<Resource<List<CoinMarketEntity>>>
*/


/*
class MovieRepository @Inject constructor(private val movieDao : MovieDao, private val invioApiService: InvioApiService) {
    val api = invioApiService
    val dao = movieDao
}*/
