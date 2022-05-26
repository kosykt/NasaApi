package com.example.nasaapi.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nasaapi.data.database.dao.CachePodDao
import com.example.nasaapi.data.database.dao.FavoritePodDao
import com.example.nasaapi.data.database.model.CachePodEntity
import com.example.nasaapi.data.database.model.FavoritePodEntity

@Database(
    entities = [CachePodEntity::class, FavoritePodEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cachePodDao(): CachePodDao
    abstract fun favoritePodDao(): FavoritePodDao
}