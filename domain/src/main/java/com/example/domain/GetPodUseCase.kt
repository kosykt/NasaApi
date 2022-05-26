package com.example.domain

import com.example.domain.model.DomainPodModel

class GetPodUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(isNetworkAvailable: Boolean, date: String): DomainPodModel {
        return when (isNetworkAvailable) {
            true -> repository.getPodFromNetwork(date)
            false -> repository.getPodFromDatabase(date)
        }
    }
}
