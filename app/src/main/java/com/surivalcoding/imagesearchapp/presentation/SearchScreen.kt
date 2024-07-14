package com.surivalcoding.imagesearchapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.surivalcoding.imagesearchapp.domain.model.PhotoInfo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchUiState,
    onFetchPhotos: (String) -> Unit = {},
    event: SharedFlow<String> = MutableSharedFlow()
) {
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    var query by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        event.collectLatest { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short,
            )
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Image Search")
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { newValue -> query = newValue },
                textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onFetchPhotos(query)
                        }
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                        )
                    }
                },
            )
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(10.dp)
                ) {
                    items(state.photos) { photo ->
                        AsyncImage(
                            model = photo.previewURL,
                            contentDescription = photo.tags,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10))
                                .aspectRatio(1f)
                        )
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        state = SearchUiState(
            isLoading = false,
            photos = listOf(
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
            ),
        )
    )
}

@Preview
@Composable
fun SearchScreenLoadingPreview() {
    SearchScreen(
        state = SearchUiState(
            isLoading = true,
            photos = listOf()
        )
    )
}