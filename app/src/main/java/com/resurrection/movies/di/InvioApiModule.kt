package com.resurrection.movies.di

import com.resurrection.movies.data.remote.InvioApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object InvioApiModule {

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit =
         Retrofit.Builder()
            .baseUrl("http://www.omdbapi.com/")
             .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun createApi(retrofit: Retrofit): InvioApiService =
        retrofit.create(InvioApiService::class.java)


}
