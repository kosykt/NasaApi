package com.example.nasaapi.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nasaapi.App
import com.example.nasaapi.data.database.dao.CachePodDao
import com.example.nasaapi.data.database.dao.FavoritePodDao
import com.example.nasaapi.data.database.model.CachePodEntity
import com.example.nasaapi.data.database.model.FavoritePodEntity

@Database(
    entities = [CachePodEntity::class, FavoritePodEntity::class],
    version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cachePodDao(): CachePodDao
    abstract fun favoritePodDao(): FavoritePodDao

    companion object {
        private const val DB_NAME = "database.db"

        val instance by lazy {
            Room.databaseBuilder(App.instance, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}