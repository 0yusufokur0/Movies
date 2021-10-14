package com.resurrection.movies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.resurrection.movies.data.db.dao.MovieDao
import com.resurrection.movies.data.model.SearchItem

@Database(
    entities = [SearchItem::class],
    version = 1
)

abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
