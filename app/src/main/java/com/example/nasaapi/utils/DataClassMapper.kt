package com.example.nasaapi.utils

import com.example.nasaapi.data.database.model.PodEntity
import com.example.nasaapi.data.network.model.PodDTO
import com.example.nasaapi.domain.model.DomainPodModel

fun PodDTO.toDomainPodModel() = DomainPodModel(
    date = this.date,
    explanation = this.explanation,
    hdUrl = this.hdUrl.orEmpty(),
    mediaType = this.mediaType,
    serviceVersion = this.serviceVersion,
    title = this.title,
    url = this.url,
)

fun PodDTO.toPodEntity() = PodEntity(
    date = this.date,
    explanation = this.explanation,
    hdUrl = this.hdUrl.orEmpty(),
    mediaType = this.mediaType,
    serviceVersion = this.serviceVersion,
    title = this.title,
    url = this.url,
)

fun PodEntity.toDomainPodModel() = DomainPodModel(
    date = this.date,
    explanation = this.explanation,
    hdUrl = this.hdUrl,
    mediaType = this.mediaType,
    serviceVersion = this.serviceVersion,
    title = this.title,
    url = this.url,
)