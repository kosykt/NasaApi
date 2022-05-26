package com.example.domain

class DeleteFavoritePodUseCase(
    private val repository: DomainRepository
) {
    suspend fun execute(date: String) = repository.deleteFavoritePod(date)
}