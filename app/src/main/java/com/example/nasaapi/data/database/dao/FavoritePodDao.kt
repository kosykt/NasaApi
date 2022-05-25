package com.example.nasaapi.data.database.dao

import androidx.room.*
import com.example.nasaapi.data.database.model.FavoritePodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoritePodEntity: FavoritePodEntity)

    @Delete
    suspend fun delete(favoritePodEntity: FavoritePodEntity)

    @Query("SELECT * FROM FavoritePodEntity")
    fun getAll(): Flow<List<FavoritePodEntity>>
}