package com.example.acharyaprashant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.acharyaprashant.model.unsplash.UnsplashPhoto
import com.example.acharyaprashant.ui.theme.AcharyaPrashantTheme
import com.example.acharyaprashant.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AcharyaPrashantTheme {
                val viewModel: MainViewModel = viewModel()
                val photos by viewModel.photos.collectAsState()
                val loading by viewModel.loading.collectAsState()

                ImageGrid(photos, loading) {
                    viewModel.fetchPhotos()
                }
            }
        }
    }
}
@Composable
fun ImageGrid(photos: List<UnsplashPhoto>, loading: Boolean, onLoadMore: () -> Unit) {
    val scrollState = rememberLazyGridState()
    LazyVerticalGrid(
        state = scrollState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp),
        content = {
            items(photos) { photo ->
                ImageListItem(photo = photo)
            }
            if (loading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        },
        modifier = Modifier
            .onGloballyPositioned { coordinates ->
                val fullyVisibleItemIndex = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                if (fullyVisibleItemIndex == photos.size - 1) {
                    onLoadMore()
                }
            }
    )
}

@Composable
fun ImageListItem(photo: UnsplashPhoto) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .fillMaxWidth(),
    ) {
        val imagePainter = rememberImagePainter(
            data = photo.urls.regular,
            builder = {
                crossfade(true)
            }
        )
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}