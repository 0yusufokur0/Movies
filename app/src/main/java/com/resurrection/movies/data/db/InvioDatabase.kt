package com.resurrection.movies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.resurrection.movies.data.db.dao.InvioDao
import com.resurrection.movies.data.db.entity.InvioModel

@Database(
    entities = [InvioModel::class],
    version = 1
)

abstract class InvioDatabase : RoomDatabase() {
    abstract fun invioDao(): InvioDao
}
