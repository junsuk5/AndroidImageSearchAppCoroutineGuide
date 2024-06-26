package com.surivalcoding.imagesearchapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PhotoInfo(
    val id: Int,
    val previewURL: String,
    val tags: String,
)