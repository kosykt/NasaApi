package com.example.nasaapi.domain

class DeleteFavoritePodUseCase(
    private val repository: DomainRepository
) {
    suspend operator fun invoke(date: String) = repository.deleteFavoritePod(date)
}