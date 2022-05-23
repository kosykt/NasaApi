package com.example.nasaapi.data.network.model

import com.google.gson.annotations.SerializedName

data class PodDTO(
    val date: String,
    val explanation: String,
    @SerializedName("hdurl")
    val hdUrl: String?,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("service_version")
    val serviceVersion: String,
    val title: String,
    val url: String
)