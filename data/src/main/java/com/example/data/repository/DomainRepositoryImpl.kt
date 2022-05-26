package com.example.data.repository

import com.example.data.database.model.FavoritePodEntity
import com.example.data.network.model.PodDTO
import com.example.data.toDomainPodModel
import com.example.data.toListString
import com.example.data.toPodEntity
import com.example.domain.DomainRepository
import com.example.domain.model.DomainPodModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DomainRepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository
) : DomainRepository {

    override suspend fun getPodFromNetwork(date: String): DomainPodModel {
        return networkRepository.getPod(date)
            .also {
                cachePod(it)
            }.toDomainPodModel()
    }

    private suspend fun cachePod(podDTO: PodDTO) {
        databaseRepository.insertCachePodEntity(podDTO.toPodEntity())
    }

    override suspend fun getPodFromDatabase(date: String): DomainPodModel {
        return databaseRepository.getCachePodEntityByData(date).toDomainPodModel()
    }

    override suspend fun saveFavoritePod(date: String) {
        databaseRepository.insertFavoritePodEntity(FavoritePodEntity(date))
    }

    override suspend fun deleteFavoritePod(date: String) {
        databaseRepository.deleteFavoritePodEntity(FavoritePodEntity(date))
    }

    override fun getAllFavoritePods(): Flow<List<String>> {
        return databaseRepository.getAllFavoritePodEntity().map { it.toListString() }
    }
}