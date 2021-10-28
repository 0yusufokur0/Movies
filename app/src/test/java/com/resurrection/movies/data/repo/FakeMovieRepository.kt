package com.resurrection.movies.data.repo

import androidx.lifecycle.MutableLiveData
import com.resurrection.movies.data.model.MovieDetails
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.data.model.SearchResults
import com.resurrection.movies.data.repository.MovieRepository
import com.resurrection.movies.util.Resource
import com.resurrection.movies.util.getResourceByDatabaseRequest
import com.resurrection.movies.util.getResourceByNetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.bouncycastle.jcajce.provider.digest.MD2
import retrofit2.Response

class FakeMovieRepository :MovieRepository{

    private val movies = mutableListOf<SearchItem>()
    private val moviesLiveData = MutableLiveData<List<SearchItem>>(movies)

    private lateinit var movieDetails: MovieDetails

    override suspend fun getMovieById(id: String, page: Int): Flow<Resource<SearchResults>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieById(imdbID: String): Flow<Resource<SearchItem>> = flow{
        emit(getResourceByDatabaseRequest { getMovieByID() })
    }

    override suspend fun getMovieDetail(imdbId: String): Flow<Resource<MovieDetails>>  = flow{
        emit(getResourceByNetworkRequest { movieDetail() })
    }

    override suspend fun insertMovie(movie: SearchItem): Flow<Resource<Unit>> = flow {
        emit( getResourceByDatabaseRequest { addMovies(movie)})
    }

    override suspend fun removeMovie(movie: SearchItem): Flow<Resource<Unit>> = flow{
        emit(getResourceByDatabaseRequest { removeMovies(movie) })
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

    private suspend fun addMovies(movie: SearchItem){
        movies.add(movie)
        refresh()
    }

    private suspend fun removeMovies(movie: SearchItem){
        movies.remove(movie)
        refresh()
    }

   private suspend fun movieDetail(): Response<MovieDetails> {
       movieDetails = MovieDetails("", "", "", "", "", "", "", "", "", "", "", "","","","", "", listOf(), "", "", "", "", "","","","")
       var temp: Response<MovieDetails> = Response.success(movieDetails)
       return temp
   }

    private suspend fun getMovieByID(): SearchItem {
        return SearchItem("",null,null,null,null)
    }

}