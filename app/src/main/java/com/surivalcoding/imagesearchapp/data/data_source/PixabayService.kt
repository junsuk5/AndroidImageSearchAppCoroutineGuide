package com.surivalcoding.imagesearchapp.data.data_source

import com.surivalcoding.imagesearchapp.data.dto.PhotoResult
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {

    @GET("api")
    suspend fun getPhotos(
        @Query("key") apiKey: String,
        @Query("q") query: String,
        @Query("image_type") imageType: String = "photo",
    ): PhotoResult

    companion object {
        private const val BASE_URL = "https://pixabay.com/"

        fun instance(): PixabayService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create()
        }
    }
}