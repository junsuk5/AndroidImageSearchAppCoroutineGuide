package com.surivalcoding.imagesearchapp.data.repository

import com.surivalcoding.imagesearchapp.core.Result
import com.surivalcoding.imagesearchapp.data.data_source.PixabayService
import com.surivalcoding.imagesearchapp.domain.model.PhotoInfo
import com.surivalcoding.imagesearchapp.domain.repository.PhotoRepository

class RetrofitPixabayPhotoRepositoryImpl(
    private val apiKey: String,
    private val pixabayService: PixabayService
) : PhotoRepository {

    override suspend fun getPhotos(query: String): Result<List<PhotoInfo>, Exception> {
        try {
            println("RetrofitPixabayPhotoRepositoryImpl : ${Thread.currentThread().name}")

            val photoResult = pixabayService.getPhotos(apiKey, query)
            return Result.Success(photoResult.hits)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}