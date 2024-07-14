package com.surivalcoding.imagesearchapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.surivalcoding.imagesearchapp.presentation.SearchScreen
import com.surivalcoding.imagesearchapp.presentation.SearchViewModel
import com.surivalcoding.imagesearchapp.ui.theme.ImageSearchAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageSearchAppTheme {
                val viewModel: SearchViewModel = viewModel(
                    factory = SearchViewModel.Factory
                )
                val state by viewModel.state.collectAsState()

                SearchScreen(
                    state = state,
                    onFetchPhotos = { query ->
                        viewModel.fetchPhotos(query)
                    },
                    event = viewModel.event,
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ImageSearchAppTheme {
        Greeting("Android")
    }
}