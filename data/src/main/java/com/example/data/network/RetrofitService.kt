package com.example.data.network

import com.example.data.BuildConfig
import com.example.data.network.model.PodDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("planetary/apod")
    suspend fun getPOD(
        @Query("date") date: String,
        @Query("api_key") apiKey: String = BuildConfig.NASA_API_KEY,
    ): PodDTO
}