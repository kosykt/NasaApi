package com.example.nasaapi.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nasaapi.data.database.model.CachePodEntity

@Dao
interface CachePodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: CachePodEntity)

    @Query("SELECT * FROM CachePodEntity WHERE date =:date")
    fun getByDate(date: String): CachePodEntity
}