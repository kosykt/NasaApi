package com.example.nasaapi.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CachePodEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val explanation: String,
    val hdUrl: String,
    val mediaType: String,
    val serviceVersion: String,
    val title: String,
    val url: String
)