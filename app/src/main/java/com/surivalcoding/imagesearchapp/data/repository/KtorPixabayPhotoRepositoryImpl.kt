package com.surivalcoding.imagesearchapp.data.repository

import com.surivalcoding.imagesearchapp.core.Result
import com.surivalcoding.imagesearchapp.domain.model.PhotoInfo
import com.surivalcoding.imagesearchapp.data.dto.PhotoResult
import com.surivalcoding.imagesearchapp.domain.repository.PhotoRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class KtorPixabayPhotoRepositoryImpl(
    private val apiKey: String
) : PhotoRepository {

    private val client = HttpClient(CIO)
    private val json = Json {
        ignoreUnknownKeys = true
    }

    override suspend fun getPhotos(query: String): Result<List<PhotoInfo>, Exception> =
        withContext(Dispatchers.IO) {
            try {
                println("KtorPixabayPhotoRepositoryImpl : ${Thread.currentThread().name}")

                val response: HttpResponse =
                    client.get("https://pixabay.com/api/?key=$apiKey&q=$query&image_type=photo")

                val result = json.decodeFromString<PhotoResult>(response.body())
                Result.Success(result.hits)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}