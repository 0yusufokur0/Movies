package com.resurrection.movies.data.repository

import com.resurrection.movies.data.db.dao.InvioDao
import com.resurrection.movies.data.remote.InvioApiService
import javax.inject.Inject

class InvioRepository @Inject constructor(private val invioDao : InvioDao, private val invioApiService: InvioApiService) {
/*    private val gson = GsonBuilder().setLenient().create()
    private val retrofit = Retrofit.Builder().baseUrl("http://www.omdbapi.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val api = retrofit.create(InvioApiService::class.java)*/

    val api = invioApiService
    val dao = invioDao
}