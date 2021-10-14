package com.resurrection.movies.di

import android.content.Context
import androidx.room.Room
import com.resurrection.movies.data.db.InvioDatabase
import com.resurrection.movies.data.db.dao.InvioDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object InvioDaoModule {

    @Provides
    @Singleton
    fun invioDatabase(@ApplicationContext context: Context): InvioDatabase =
        Room.databaseBuilder(context, InvioDatabase::class.java, "crypto").build()


    @Provides
    @Singleton
    fun invioDao(cryptoDatabase: InvioDatabase): InvioDao =
        cryptoDatabase.invioDao()
}

