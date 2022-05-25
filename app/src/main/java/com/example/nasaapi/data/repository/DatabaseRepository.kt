package com.example.nasaapi.data.repository

import com.example.nasaapi.data.database.model.CachePodEntity
import com.example.nasaapi.data.database.model.FavoritePodEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    suspend fun insertCachePodEntity(cachePodEntity: CachePodEntity)
    suspend fun getCachePodEntityByData(date: String): CachePodEntity
    suspend fun insertFavoritePodEntity(favoritePodEntity: FavoritePodEntity)
    suspend fun deleteFavoritePodEntity(favoritePodEntity: FavoritePodEntity)
    fun getAllFavoritePodEntity(): Flow<List<FavoritePodEntity>>
}