package com.surivalcoding.imagesearchapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.surivalcoding.ImageSearchApp
import com.surivalcoding.imagesearchapp.core.Result
import com.surivalcoding.imagesearchapp.domain.use_case.SearchError
import com.surivalcoding.imagesearchapp.domain.use_case.SearchPhotoUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchPhotoUseCase: SearchPhotoUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchUiState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<String>()
    val event = _event.asSharedFlow()

    private var fetchJob: Job? = null

    fun fetchPhotos(query: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            println("ViewModel : ${Thread.currentThread().name}")

            delay(200)

            _state.update {
                it.copy(
                    isLoading = true,
                )
            }

            when (val searchResult = searchPhotoUseCase.invoke(query)) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            photos = searchResult.data
                        )
                    }
                }

                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                        )
                    }
                    val message = when (searchResult.error) {
                        SearchError.EmptyQuery -> "빈 쿼리입니다"
                        SearchError.NetworkError -> "네트워크 에러입니다"
                        SearchError.UnknownError -> "알 수 없는 에러입니다"
                    }
                    _event.emit(message)
                }

            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ImageSearchApp)
                SearchViewModel(
                    searchPhotoUseCase = application.searchUseCase,
                )
            }
        }
    }
}