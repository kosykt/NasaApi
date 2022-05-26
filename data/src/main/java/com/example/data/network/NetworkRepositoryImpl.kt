package com.example.data.network

import com.example.data.network.model.PodDTO
import com.example.data.repository.NetworkRepository

class NetworkRepositoryImpl(
    private val retrofitService: RetrofitService
) : NetworkRepository {

    override suspend fun getPod(date: String): PodDTO {
        return retrofitService.getPOD(date)
    }
}