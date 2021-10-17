package com.resurrection.movies.di

import com.resurrection.movies.data.remote.MovieApiService
import com.resurrection.movies.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieApiModule {

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun createApi(retrofit: Retrofit): MovieApiService =
        retrofit.create(MovieApiService::class.java)

}
