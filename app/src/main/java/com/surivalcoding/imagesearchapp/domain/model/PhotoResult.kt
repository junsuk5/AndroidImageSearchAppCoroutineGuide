package com.surivalcoding.imagesearchapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PhotoResult(
    val total: Int,
    val totalHits: Int,
    val hits: List<PhotoInfo>,
)