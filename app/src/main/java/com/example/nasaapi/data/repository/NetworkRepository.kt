package com.example.nasaapi.data.repository

import com.example.nasaapi.data.network.model.PodDTO

interface NetworkRepository {

    suspend fun getPod(date: String): PodDTO
}