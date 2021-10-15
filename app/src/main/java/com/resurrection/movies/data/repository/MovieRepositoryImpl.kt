package com.resurrection.movies.data.repository

import com.resurrection.movies.data.db.dao.MovieDao
import com.resurrection.movies.data.model.MovieDetails
import com.resurrection.movies.data.model.SearchItem
import com.resurrection.movies.data.model.SearchResults
import com.resurrection.movies.data.remote.InvioApiService
import com.resurrection.movies.util.Resource
import com.resurrection.movies.util.getResourceByDatabaseRequest
import com.resurrection.movies.util.getResourceByNetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieApiService: InvioApiService, private val movieDao: MovieDao,
): MovieRepository {
    override suspend fun getMovieById(
        id: String,
        apiKey: String,
        page: Int
    ): Flow<Resource<SearchResults>> = flow {
        emit(getResourceByNetworkRequest {  movieApiService.getMovieById(id,apiKey,page)})
    }

    override suspend fun getMovieDetail(
        imdbId: String,
        apiKey: String
    ): Flow<Resource<MovieDetails>> = flow {
        emit(getResourceByNetworkRequest {  movieApiService.getMovieDetail(imdbId,apiKey)})
    }

    override suspend fun getMovieById(imdbID: String): Flow<Resource<SearchItem>> = flow {
        emit(getResourceByDatabaseRequest { movieDao.getMovieById(imdbID) })
    }


    override suspend fun insertMovie(movie: SearchItem): Flow<Resource<Unit>> = flow{
        emit(getResourceByDatabaseRequest { movieDao.insertMovie(movie) })
    }

    override suspend fun removeMovie(movie: SearchItem): Flow<Resource<Unit>> = flow{
        emit(getResourceByDatabaseRequest { movieDao.removeMovie(movie) })
    }

    override suspend fun getFavoriteMovies(): Flow<Resource<List<SearchItem>>> = flow {
        emit(getResourceByDatabaseRequest { movieDao.getFavoriteMovies() })
    }


}


/*
class CoinRepositoryImpl @Inject constructor(
    private val coinApiService: CoinApiService,
    private val coinDao: CoinDao
) : CoinRepository {

    override suspend fun getAllCoins(): Flow<Resource<List<CoinMarketItem>>> = flow {
        emit(getResourceByNetworkRequest { coinApiService.getAllCoins() })
    }

    override suspend fun getCoinByID(id: String): Flow<Resource<CoinDetailItem>> = flow {
        emit(getResourceByNetworkRequest { coinApiService.getCoinByID(id) })
    }

    override suspend fun insertAllCoins(listCrypto: List<CoinMarketEntity>): Flow<Resource<Unit>> =
        flow {
            emit(getResourceByDatabaseRequest { coinDao.insertAllCrypto(listCrypto) })
        }

    override suspend fun getCoinsByParameter(parameter: String): Flow<Resource<List<CoinMarketEntity>>> =
        flow {
            emit(getResourceByDatabaseRequest { coinDao.getCryptoByParameter(parameter) })
        }
}*/
