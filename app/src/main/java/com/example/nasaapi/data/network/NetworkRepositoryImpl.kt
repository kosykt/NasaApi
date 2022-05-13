package com.example.nasaapi.data.network

import com.example.nasaapi.data.network.model.PodDTO
import com.example.nasaapi.data.repository.NetworkRepository

class NetworkRepositoryImpl(
    private val retrofitService: RetrofitService
) : NetworkRepository {

    override suspend fun getPod(date: String): PodDTO {
        return retrofitService.getPOD(date)
    }
}