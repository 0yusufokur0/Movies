package com.resurrection.movies.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.resurrection.movies.data.model.SearchItem

@Dao
interface InvioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie:SearchItem)

    @Query("SELECT * FROM searchitem")
    suspend fun getCryptoFavorite(): List<SearchItem>
}