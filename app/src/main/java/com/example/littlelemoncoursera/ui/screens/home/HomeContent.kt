package com.example.littlelemoncoursera.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemoncoursera.R
import com.example.littlelemoncoursera.navigation.Routes
import com.example.littlelemoncoursera.ui.screens.components.LogoImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(navController: NavController) {
    Scaffold(
        topBar = {
            HomeAppBar(
                onNavigateToSearchPage={
                    navController.navigate(Routes.SEARCH.name)
                }
            )
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Home Content")
        }
    }
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
                    contentDescription = "Search Items"
                )
            }
        }
    )
}
