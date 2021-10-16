package com.resurrection.movies.data.repository

import com.resurrection.movies.data.db.dao.MovieDao
import com.resurrection.movies.data.model.MovieDetails
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.data.model.SearchResults
import com.resurrection.movies.data.remote.MovieApiService
import com.resurrection.movies.util.Resource
import com.resurrection.movies.util.getResourceByDatabaseRequest
import com.resurrection.movies.util.getResourceByNetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService, private val movieDao: MovieDao,
) : MovieRepository {
    override suspend fun getMovieById(
        id: String,
        page: Int
    ): Flow<Resource<SearchResults>> = flow {
        emit(getResourceByNetworkRequest { movieApiService.getMovieById(id, page) })
    }

    override suspend fun getMovieDetail(
        imdbId: String,
    ): Flow<Resource<MovieDetails>> = flow {
        emit(getResourceByNetworkRequest { movieApiService.getMovieDetail(imdbId) })
    }

    override suspend fun getMovieById(imdbID: String): Flow<Resource<SearchItem>> = flow {
        emit(getResourceByDatabaseRequest { movieDao.getMovieById(imdbID) })
    }
    override suspend fun getMovieByTitle(title: String): Flow<Resource<List<SearchItem>>> = flow {
        emit(getResourceByDatabaseRequest { movieDao.getMovieByTitle(title) })
    }


    override suspend fun insertMovie(movie: SearchItem): Flow<Resource<Unit>> = flow {
        emit(getResourceByDatabaseRequest { movieDao.insertMovie(movie) })
    }

    override suspend fun removeMovie(movie: SearchItem): Flow<Resource<Unit>> = flow {
        emit(getResourceByDatabaseRequest { movieDao.removeMovie(movie) })
    }

    override suspend fun getFavoriteMovies(): Flow<Resource<List<SearchItem>>> = flow {
        emit(getResourceByDatabaseRequest { movieDao.getFavoriteMovies() })
    }



}
