package com.example.nasaapi.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nasaapi.data.database.model.PodEntity

@Dao
interface PodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: PodEntity)

    @Query("SELECT * FROM PodEntity WHERE date =:date")
    fun getByDate(date: String): PodEntity
}