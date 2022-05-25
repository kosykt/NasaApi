package com.example.nasaapi.data.database

import com.example.nasaapi.data.database.model.CachePodEntity
import com.example.nasaapi.data.repository.DatabaseRepository

class DatabaseRepositoryImpl(
    private val database: AppDatabase
) : DatabaseRepository {

    override suspend fun insertCachePodEntity(cachePodEntity: CachePodEntity) {
        database.cachePodDao().insert(cachePodEntity)
    }

    override suspend fun getCachePodEntityByData(date: String): CachePodEntity {
        return database.cachePodDao().getByDate(date)
    }
}