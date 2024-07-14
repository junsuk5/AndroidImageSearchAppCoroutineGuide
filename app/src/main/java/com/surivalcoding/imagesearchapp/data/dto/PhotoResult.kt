package com.surivalcoding.imagesearchapp.data.dto

import com.surivalcoding.imagesearchapp.domain.model.PhotoInfo
import kotlinx.serialization.Serializable

@Serializable
data class PhotoResult(
    val total: Int,
    val totalHits: Int,
    val hits: List<PhotoInfo>,
)