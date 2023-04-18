package com.example.littlelemoncoursera.ui.screens.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage


@Composable
fun NetworkImageLoader(imageURL:String,title:String,modifier: Modifier) {
    AsyncImage(
        model = imageURL,
        contentDescription = title,
        modifier = modifier
            .clip(RoundedCornerShape(15))
    )
}