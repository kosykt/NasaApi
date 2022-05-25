package com.example.nasaapi.domain

class GetAllFavoritePodsUseCase(
    private val repository: DomainRepository,
) {
    fun execute() = repository.getAllFavoritePods()
}