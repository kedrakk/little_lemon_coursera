package com.example.littlelemoncoursera.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import com.example.littlelemoncoursera.model.HomeBottomBarItems
import com.example.littlelemoncoursera.model.LittleLemonUser
import com.example.littlelemoncoursera.ui.screens.components.ActionButton
import com.example.littlelemoncoursera.viewmodels.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage( homeViewModel: HomeViewModel, littleLemonUser: LittleLemonUser,onLogout: () -> Unit) {

    val homeUIState = homeViewModel.uiState.collectAsState().value

    Scaffold(
        bottomBar = {
            HomeBottomBar(
                homeViewModel.bottomBarItems,
                onItemClick = {
                    homeViewModel.changeIndex(it)
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(paddingValues = it)
        ) {
            when(homeUIState.selectedIndex){
                0 -> HomeContent()
                1 -> CategoryContent()
                2 -> CartContent()
                3 -> ReservationContent()
                4 -> ProfileContent(littleLemonUser = littleLemonUser,onLogout = {onLogout()})
                else -> HomeContent()
            }
        }
    }
}

@Composable
fun HomeBottomBar(
    bottomBarItems: List<HomeBottomBarItems>,
    onItemClick: (Int) -> Unit,
) {
    BottomAppBar(containerColor = Color.Gray) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            bottomBarItems.map {item->
                ClickableText(
                    text = AnnotatedString(item.label),
                    onClick = {
                        onItemClick(item.index)
                    }
                )
            }
        }
    }
}

@Composable
fun HomeContent() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home Content")
    }
}

@Composable
fun CategoryContent() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Category Content")
    }
}

@Composable
fun CartContent() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Cart Content")
    }
}

@Composable
fun ReservationContent() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Reservation Content")
    }
}

@Composable
fun ProfileContent(littleLemonUser: LittleLemonUser,onLogout: () -> Unit) {
    Column() {
        Text(text = "Profile Content")
        Text(text = littleLemonUser.email)
        ActionButton(
            onClick = { onLogout() },
            label = "Logout"
        )
    }
}