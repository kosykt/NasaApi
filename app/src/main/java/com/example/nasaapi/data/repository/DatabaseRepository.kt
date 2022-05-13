package com.example.nasaapi.data.repository

import com.example.nasaapi.data.database.model.PodEntity

interface DatabaseRepository {

    suspend fun insert(podEntity: PodEntity)
    suspend fun getPodByData(date: String): PodEntity
}