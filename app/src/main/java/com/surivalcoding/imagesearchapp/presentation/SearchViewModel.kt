package com.surivalcoding.imagesearchapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.surivalcoding.ImageSearchApp
import com.surivalcoding.imagesearchapp.domain.repository.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchUiState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var fetchJob: Job? = null

    fun fetchPhotos(query: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isLoading = true,
                    )
                }

                val photos = photoRepository.getPhotos(query)

                _state.update {
                    it.copy(
                        isLoading = false,
                        photos = photos,
                    )
                }
                println("photo updated")
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                    )
                }
                _eventFlow.emit("네트워크 에러 : ${e.message}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val photoRepository = (this[APPLICATION_KEY] as ImageSearchApp).photoRepository
                SearchViewModel(
                    photoRepository = photoRepository,
                )
            }
        }
    }
}