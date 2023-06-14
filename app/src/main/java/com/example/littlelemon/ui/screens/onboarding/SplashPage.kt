package com.example.littlelemon.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.littlelemon.ui.screens.components.LogoImage

@Composable
fun SplashPage() {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.onPrimary), contentAlignment = Alignment.Center) {
        LogoImage(size = 500.dp)
    }
}