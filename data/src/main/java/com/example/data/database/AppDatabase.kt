package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.CachePodDao
import com.example.data.database.dao.FavoritePodDao
import com.example.data.database.model.CachePodEntity
import com.example.data.database.model.FavoritePodEntity

@Database(
    entities = [CachePodEntity::class, FavoritePodEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cachePodDao(): CachePodDao
    abstract fun favoritePodDao(): FavoritePodDao
}