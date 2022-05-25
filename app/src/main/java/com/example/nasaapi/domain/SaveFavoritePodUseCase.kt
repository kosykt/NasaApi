package com.example.nasaapi.domain

class SaveFavoritePodUseCase(
    private val repository: DomainRepository
){
    suspend operator fun invoke(date: String) = repository.saveFavoritePod(date)
}
