package com.example.nasaapi.data.repository

import com.example.nasaapi.data.database.model.CachePodEntity

interface DatabaseRepository {

    suspend fun insertCachePodEntity(cachePodEntity: CachePodEntity)
    suspend fun getCachePodEntityByData(date: String): CachePodEntity
}