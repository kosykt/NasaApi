package com.example.nasaapi.domain

import com.example.nasaapi.domain.model.DomainPodModel

interface DomainRepository {

    suspend fun getPodFromNetwork(date: String): DomainPodModel
    suspend fun getPodFromDatabase(date: String): DomainPodModel
}