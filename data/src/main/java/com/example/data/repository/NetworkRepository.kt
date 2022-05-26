package com.example.data.repository

import com.example.data.network.model.PodDTO

interface NetworkRepository {

    suspend fun getPod(date: String): PodDTO
}