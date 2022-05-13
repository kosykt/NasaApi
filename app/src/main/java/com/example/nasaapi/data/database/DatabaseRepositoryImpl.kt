package com.example.nasaapi.data.database

import com.example.nasaapi.data.database.model.PodEntity
import com.example.nasaapi.data.repository.DatabaseRepository

class DatabaseRepositoryImpl(
    private val database: AppDatabase
) : DatabaseRepository {

    override suspend fun insert(podEntity: PodEntity) {
        database.podDao().insert(podEntity)
    }

    override suspend fun getPodByData(date: String): PodEntity {
        return database.podDao().getByDate(date)
    }
}