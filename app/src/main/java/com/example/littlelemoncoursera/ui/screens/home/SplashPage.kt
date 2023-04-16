package com.example.littlelemoncoursera.ui.screens.home

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemoncoursera.ui.screens.components.LogoImage
import kotlinx.coroutines.delay

@Composable
fun SplashPage() {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.onPrimary), contentAlignment = Alignment.Center) {
        LogoImage(size = 500.dp)
    }
}