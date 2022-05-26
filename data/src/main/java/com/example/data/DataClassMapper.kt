package com.example.data

import com.example.data.database.model.CachePodEntity
import com.example.data.database.model.FavoritePodEntity
import com.example.data.network.model.PodDTO
import com.example.domain.model.DomainPodModel

fun PodDTO.toDomainPodModel() = DomainPodModel(
    date = this.date,
    explanation = this.explanation,
    hdUrl = this.hdUrl.orEmpty(),
    mediaType = this.mediaType,
    serviceVersion = this.serviceVersion,
    title = this.title,
    url = this.url,
)

fun PodDTO.toPodEntity() = CachePodEntity(
    date = this.date,
    explanation = this.explanation,
    hdUrl = this.hdUrl.orEmpty(),
    mediaType = this.mediaType,
    serviceVersion = this.serviceVersion,
    title = this.title,
    url = this.url,
)

fun CachePodEntity.toDomainPodModel() = DomainPodModel(
    date = this.date,
    explanation = this.explanation,
    hdUrl = this.hdUrl,
    mediaType = this.mediaType,
    serviceVersion = this.serviceVersion,
    title = this.title,
    url = this.url,
)

fun List<FavoritePodEntity>.toListString() = this.map {
    it.date
}