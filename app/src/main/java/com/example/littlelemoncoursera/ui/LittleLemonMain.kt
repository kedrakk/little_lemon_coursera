package com.example.littlelemoncoursera.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.littlelemoncoursera.ui.screens.onboarding.RegisterPage
import com.example.littlelemoncoursera.viewmodels.onboarding.OnboardingViewModel

@Composable
fun LittleLemonMainPage() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        RegisterPage(viewModel = OnboardingViewModel())
    }
}