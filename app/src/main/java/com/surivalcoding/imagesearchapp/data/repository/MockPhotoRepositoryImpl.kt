package com.surivalcoding.imagesearchapp.data.repository

import com.surivalcoding.imagesearchapp.core.Result
import com.surivalcoding.imagesearchapp.domain.model.PhotoInfo
import com.surivalcoding.imagesearchapp.domain.repository.PhotoRepository
import kotlinx.coroutines.delay

class MockPhotoRepositoryImpl : PhotoRepository {

    override suspend fun getPhotos(query: String): Result<List<PhotoInfo>, Exception> {
        println("MockPhotoRepositoryImpl : ${Thread.currentThread().name}")
        delay(1000L)
        return Result.Success(
            listOf(
                PhotoInfo(
                    id = 0,
                    previewURL = "https://cdnimg.melon.co.kr/cm2/artistcrop/images/002/61/143/261143_20210325180240_org.jpg?61e575e8653e5920470a38d1482d7312/melon/optimize/90",
                    tags = "아이유, 여신"
                ),
                PhotoInfo(
                    id = 1,
                    previewURL = "https://image.bugsm.co.kr/album/images/500/40271/4027185.jpg",
                    tags = "아이유, 가수"
                ),
                PhotoInfo(
                    id = 2,
                    previewURL = "https://img.gqkorea.co.kr/gq/2022/08/style_63073140eea70.jpg",
                    tags = "아이유, 연기자"
                ),
            )
        )
    }
}