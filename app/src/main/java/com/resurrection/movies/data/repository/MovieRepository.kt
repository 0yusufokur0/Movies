package com.resurrection.movies.data.repository

import com.resurrection.movies.data.db.dao.MovieDao
import com.resurrection.movies.data.remote.InvioApiService
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieDao : MovieDao, private val invioApiService: InvioApiService) {
    val api = invioApiService
    val dao = movieDao
}