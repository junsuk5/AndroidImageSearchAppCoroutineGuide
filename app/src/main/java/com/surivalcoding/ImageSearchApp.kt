package com.surivalcoding

import android.app.Application
import com.surivalcoding.imagesearchapp.BuildConfig
import com.surivalcoding.imagesearchapp.data.repository.KtorPhotoRepositoryImpl
import com.surivalcoding.imagesearchapp.data.repository.MockPhotoRepositoryImpl
import com.surivalcoding.imagesearchapp.domain.repository.PhotoRepository

class ImageSearchApp : Application() {

    val photoRepository: PhotoRepository by lazy {
        MockPhotoRepositoryImpl()
//        KtorPhotoRepositoryImpl(apiKey = BuildConfig.API_KEY)
    }
}