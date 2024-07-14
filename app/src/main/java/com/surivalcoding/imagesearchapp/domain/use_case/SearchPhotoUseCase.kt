package com.surivalcoding.imagesearchapp.domain.use_case

import com.surivalcoding.imagesearchapp.core.Result
import com.surivalcoding.imagesearchapp.domain.model.PhotoInfo
import com.surivalcoding.imagesearchapp.domain.repository.PhotoRepository

sealed class SearchError {
    data object EmptyQuery : SearchError()
    data object NetworkError : SearchError()
    data object UnknownError : SearchError()
}

class SearchPhotoUseCase(
    private val photoRepository: PhotoRepository
) {
    suspend operator fun invoke(query: String): Result<List<PhotoInfo>, SearchError> {
        if (query.isEmpty()) {
            return Result.Error(SearchError.EmptyQuery)
        }

        return when (val photosResult = photoRepository.getPhotos(query)) {
            is Result.Error -> Result.Error(SearchError.UnknownError)
            is Result.Success -> Result.Success(photosResult.data)
        }
    }
}