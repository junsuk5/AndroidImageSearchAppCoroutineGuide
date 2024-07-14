package com.surivalcoding

import android.app.Application
import com.surivalcoding.imagesearchapp.BuildConfig
import com.surivalcoding.imagesearchapp.data.data_source.PixabayService
import com.surivalcoding.imagesearchapp.data.repository.KtorPixabayPhotoRepositoryImpl
import com.surivalcoding.imagesearchapp.data.repository.MockPhotoRepositoryImpl
import com.surivalcoding.imagesearchapp.data.repository.RetrofitPixabayPhotoRepositoryImpl
import com.surivalcoding.imagesearchapp.domain.repository.PhotoRepository
import com.surivalcoding.imagesearchapp.domain.use_case.SearchPhotoUseCase

class ImageSearchApp : Application() {

    private val photoRepository: PhotoRepository by lazy {
        MockPhotoRepositoryImpl()
//        KtorPixabayPhotoRepositoryImpl(apiKey = BuildConfig.API_KEY)
//        RetrofitPixabayPhotoRepositoryImpl(
//            apiKey = BuildConfig.API_KEY,
//            pixabayService = PixabayService.instance(),
//        )
    }

    val searchUseCase by lazy {
        SearchPhotoUseCase(photoRepository)
    }
}
