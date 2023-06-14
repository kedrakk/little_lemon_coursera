package com.example.littlelemon.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.littlelemoncoursera.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonAppBar(
    title: String, onBackClicked: () -> Unit, isBackIconContains: Boolean = true,
) {
    TopAppBar(
        title = {
            Text(text = title, style = MaterialTheme.typography.bodyMedium)
        },
        navigationIcon = {
            if (isBackIconContains)
                IconButton(onClick = { onBackClicked() }) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = stringResource(R.string.back),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                    )
                }
        },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(onNavigateToSearchPage: () -> Unit) {
    TopAppBar(
        title = {
            LogoImage(size = 100.dp)
        },
        actions = {
            IconButton(
                onClick = { onNavigateToSearchPage() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = stringResource(R.string.search_items),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                )
            }
        }
    )
}
