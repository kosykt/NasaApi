package com.example.nasaapi.domain

import com.example.nasaapi.domain.model.DomainPodModel
import kotlinx.coroutines.flow.Flow

interface DomainRepository {

    suspend fun getPodFromNetwork(date: String): DomainPodModel
    suspend fun getPodFromDatabase(date: String): DomainPodModel
    suspend fun saveFavoritePod(date: String)
    suspend fun deleteFavoritePod(date: String)
    fun getAllFavoritePods(): Flow<List<String>>
}