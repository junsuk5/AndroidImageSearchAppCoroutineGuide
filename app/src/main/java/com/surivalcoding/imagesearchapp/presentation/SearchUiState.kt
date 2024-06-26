package com.surivalcoding.imagesearchapp.presentation

import com.surivalcoding.imagesearchapp.domain.model.PhotoInfo

data class SearchUiState(
    val isLoading: Boolean = false,
    val photos: List<PhotoInfo> = emptyList(),
    val isError: Boolean = false,
)