package com.example.domain

class SaveFavoritePodUseCase(
    private val repository: DomainRepository
){
    suspend fun execute(date: String) = repository.saveFavoritePod(date)
}
