package com.example.nasaapi.data.repository

import com.example.nasaapi.data.database.model.FavoritePodEntity
import com.example.nasaapi.data.network.model.PodDTO
import com.example.nasaapi.domain.DomainRepository
import com.example.nasaapi.domain.model.DomainPodModel
import com.example.nasaapi.utils.toDomainPodModel
import com.example.nasaapi.utils.toListString
import com.example.nasaapi.utils.toPodEntity
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