package com.example.nasaapi.data.database

import com.example.nasaapi.data.database.model.CachePodEntity
import com.example.nasaapi.data.database.model.FavoritePodEntity
import com.example.nasaapi.data.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class DatabaseRepositoryImpl(
    private val database: AppDatabase,
) : DatabaseRepository {

    override suspend fun insertCachePodEntity(cachePodEntity: CachePodEntity) {
        database.cachePodDao().insert(cachePodEntity)
    }

    override suspend fun getCachePodEntityByData(date: String): CachePodEntity {
        return database.cachePodDao().getByDate(date)
    }

    override suspend fun insertFavoritePodEntity(favoritePodEntity: FavoritePodEntity) {
        database.favoritePodDao().insert(favoritePodEntity)
    }

    override suspend fun deleteFavoritePodEntity(favoritePodEntity: FavoritePodEntity) {
        database.favoritePodDao().delete(favoritePodEntity)
    }

    override fun getAllFavoritePodEntity(): Flow<List<FavoritePodEntity>> {
        return database.favoritePodDao().getAll()
    }
}