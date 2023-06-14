package com.example.littlelemon.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NetworkImageLoader(imageURL: String, title: String, modifier: Modifier) {
    GlideImage(
        model = imageURL, contentDescription = title,
        modifier = modifier
            .clip(RoundedCornerShape(15)),
    )
}

@Composable
fun LocalImageLoader(
    modifier: Modifier,
    painterRes: Int,
    imageModifier: Modifier = Modifier.padding(0.dp)
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = painterRes),
            contentDescription = null,
            modifier = imageModifier,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
        )
    }
}