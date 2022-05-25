package com.example.nasaapi.domain

class DeleteFavoritePodUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(date: String) = repository.deleteFavoritePod(date)
}