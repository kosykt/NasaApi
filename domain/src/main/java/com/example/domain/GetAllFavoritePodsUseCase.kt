package com.example.domain

class GetAllFavoritePodsUseCase(
    private val repository: DomainRepository,
) {
    fun execute() = repository.getAllFavoritePods()
}