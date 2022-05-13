package com.example.nasaapi.data.repository

import com.example.nasaapi.data.network.model.PodDTO
import com.example.nasaapi.domain.DomainRepository
import com.example.nasaapi.domain.model.DomainPodModel
import com.example.nasaapi.utils.toDomainPodModel
import com.example.nasaapi.utils.toPodEntity

class DomainRepositoryImpl(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository
) : DomainRepository {

    override suspend fun getPodFromNetwork(date: String): DomainPodModel {
        return networkRepository.getPod(date).let {
            cachePod(it)
            it.toDomainPodModel()
        }
    }

    private suspend fun cachePod(podDTO: PodDTO) {
        databaseRepository.insert(podDTO.toPodEntity())
    }

    override suspend fun getPodFromDatabase(date: String): DomainPodModel {
        return databaseRepository.getPodByData(date).toDomainPodModel()
    }
}