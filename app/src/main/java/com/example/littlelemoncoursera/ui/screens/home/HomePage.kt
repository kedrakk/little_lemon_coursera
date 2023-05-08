package com.example.littlelemoncoursera.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.littlelemoncoursera.data.local.HomeBottomBarData
import com.example.littlelemoncoursera.data.local.local_db.LocalDishItem
import com.example.littlelemoncoursera.model.HomeBottomBarItems
import com.example.littlelemoncoursera.model.LittleLemonUser
import com.example.littlelemoncoursera.ui.screens.components.ActionButton
import com.example.littlelemoncoursera.ui.screens.profile.ProfileContent
import com.example.littlelemoncoursera.viewmodels.home.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    homeViewModel: HomeViewModel,
    littleLemonUser: LittleLemonUser,
    onLogout: () -> Unit,
    navController: NavController,
    onSearchClicked: (dishDataList:List<LocalDishItem>) -> Unit,
) {

    val homeUIState = homeViewModel.uiState.collectAsState().value

    Scaffold(
        bottomBar = {
            HomeBottomBar(
                homeViewModel.bottomBarItems,
                onItemClick = {
                    homeViewModel.changeIndex(it)
                },
                activeIndex = homeUIState.selectedIndex
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(paddingValues = it)
        ) {
            when (homeUIState.selectedIndex) {
                0 -> HomeContent(navController = navController,onSearchClicked = onSearchClicked,)
                1 -> CategoryContent()
                2 -> CartContent()
                3 -> ReservationContent()
                4 -> ProfileContent(littleLemonUser = littleLemonUser, onLogout = { onLogout() })
                else -> HomeContent(navController = navController,onSearchClicked = onSearchClicked,)
            }
        }
    }
}

@Composable
fun HomeBottomBar(
    bottomBarItems: List<HomeBottomBarItems>,
    onItemClick: (Int) -> Unit,
    activeIndex: Int,
) {
    BottomAppBar(containerColor = MaterialTheme.colorScheme.secondary) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            bottomBarItems.map { item ->
                val isActive: Boolean = activeIndex == item.index
                Button(
                    onClick = { onItemClick(item.index) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = if (isActive) MaterialTheme.colorScheme.onPrimary else Color.Gray
                    ),
                    //contentPadding = PaddingValues(0.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = item.iconData),
                            contentDescription = item.label
                        )
                        if (isActive)
                            Text(text = item.label, fontSize = 10.sp)
                    }
                }
            }
        }
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

@Preview
@Composable
fun HomeBottomBarPreview() {
    HomeBottomBar(
        HomeBottomBarData.homeBottomBarItems,
        onItemClick = {

        },
        activeIndex = 1
    )
}