package com.example.littlelemoncoursera.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.littlelemoncoursera.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonAppBar(
    title: String, onBackClicked: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = title, style = MaterialTheme.typography.bodyMedium)
        },
        navigationIcon = {
            IconButton(onClick = { onBackClicked() }) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "Back"
                )
            }
        },
    )
}