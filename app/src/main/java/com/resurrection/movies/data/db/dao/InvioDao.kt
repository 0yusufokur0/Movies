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

    @Query("SELECT * FROM search_item")
    suspend fun getFavoriteMovies(): List<SearchItem>

    @Query("SELECT * FROM search_item where  imdbId like :imdbID")
    suspend fun getMovieById(imdbID:String): SearchItem

}