package com.resurrection.movies.data.db.dao

import androidx.room.*
import com.resurrection.movies.data.model.SearchItem

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: SearchItem)

    @Delete
    suspend fun removeMovie(movie: SearchItem)

    @Query("SELECT * FROM search_item")
    suspend fun getFavoriteMovies(): List<SearchItem>

    @Query("SELECT * FROM search_item where  imdbId like :imdbID")
    suspend fun getMovieById(imdbID: String): SearchItem

}