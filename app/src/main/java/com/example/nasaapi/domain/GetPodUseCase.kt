package com.example.nasaapi.domain

import com.example.nasaapi.domain.model.DomainPodModel

class GetPodUseCase(
    private val repository: DomainRepository
) {
    suspend operator fun invoke(isNetworkAvailable: Boolean, date: String): DomainPodModel {
        return when (isNetworkAvailable) {
            true -> repository.getPodFromNetwork(date)
            false -> repository.getPodFromDatabase(date)
        }
    }
}
