package com.surivalcoding.imagesearchapp.domain.repository

import com.surivalcoding.imagesearchapp.core.Result
import com.surivalcoding.imagesearchapp.domain.model.PhotoInfo

interface PhotoRepository {
    suspend fun getPhotos(query: String): Result<List<PhotoInfo>, Exception>
}