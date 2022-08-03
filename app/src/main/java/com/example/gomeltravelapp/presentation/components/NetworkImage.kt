package com.example.gomeltravelapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.annotation.ExperimentalCoilApi
import coil.compose.*
import coil.request.ImageRequest

@OptIn(ExperimentalCoilApi::class)
@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    fadeIn: Boolean = true,
    contentScale: ContentScale = ContentScale.Crop,
    loadingContent: @Composable () -> Unit
) {
    Box(modifier) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .build()
        )
        if (painter.state is AsyncImagePainter.State.Success) {
            loadingContent()
        }

        Image(
            painter = painter,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = Modifier.fillMaxSize()
        )
        /*val painter = rememberCoilPainter(
            request = url,
            fadeIn = fadeIn
        )
        Image(
            painter = painter,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = Modifier.fillMaxSize()
        )
        if (painter.loadState is ImageLoadState.Loading) {
            loadingContent()
        }*/
    }
}
