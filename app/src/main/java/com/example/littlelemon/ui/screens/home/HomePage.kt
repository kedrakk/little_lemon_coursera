package com.example.littlelemon.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.littlelemon.data.local.HomeBottomBarData
import com.example.littlelemon.data.local.entity.LocalDishItem
import com.example.littlelemon.model.HomeBottomBarItems
import com.example.littlelemon.model.LittleLemonUser
import com.example.littlelemon.ui.screens.cart.CartContent
import com.example.littlelemon.ui.screens.category.CategoryContent
import com.example.littlelemon.ui.screens.profile.ProfileContent
import com.example.littlelemon.ui.screens.reservation.ReservationContent
import com.example.littlelemon.viewmodels.cart.CartViewModel
import com.example.littlelemon.viewmodels.home.HomeViewModel
import com.example.littlelemon.viewmodels.rsvn.ReservationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    homeViewModel: HomeViewModel,
    littleLemonUser: LittleLemonUser,
    onLogout: () -> Unit,
    navController: NavController,
    onSearchClicked: (dishDataList:List<LocalDishItem>) -> Unit,
    cartViewModel: CartViewModel,
    onCheckoutFromCart:(List<LocalDishItem>, Int)->Unit,
    onThemeChange:(Boolean) -> Unit,
    isDarkTheme:Boolean,
    onLanguageChanged: (String) -> Unit,
    currentLangCode: String
) {
    var categoryName by remember {
        mutableStateOf("")
    }
    val homeUIState = homeViewModel.uiState.collectAsState().value
    val categories =
        homeViewModel.getAllCategories().observeAsState(initial = emptyList())
    val localDishes =
        homeViewModel.getLocalDishes(categoryName = categoryName).observeAsState(initial = emptyList())

    Scaffold(
        bottomBar = {
            HomeBottomBar(
                homeViewModel.bottomBarItems,
                onItemClick = {
                    homeViewModel.changeIndex(it)
                },
                activeIndex = homeUIState.selectedIndex,
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(paddingValues = it)
        ) {
            when (homeUIState.selectedIndex) {
                0 -> HomeContent(
                    navController = navController,
                    onSearchClicked = onSearchClicked,
                    categories = categories.value,
                    menuItems = localDishes.value,
                    onCategoryItemClicked = {name->
                        categoryName = if(categoryName.isNotEmpty() && categoryName==name){
                            ""
                        }else{
                            name
                        }
                    },
                    selectedCategory = categoryName,
                )
                1 -> CategoryContent(
                    navController = navController,
                    categories = categories.value,
                    menuItems = localDishes.value,
                    onCategoryItemClicked = {name->
                        categoryName = if(categoryName.isNotEmpty() && categoryName==name){
                            ""
                        }else{
                            name
                        }
                    },
                    selectedCategory = categoryName,
                )
                2 -> CartContent(
                    cartViewModel = cartViewModel,
                    onCheckoutFromCart = onCheckoutFromCart
                )
                3 -> ReservationContent(
                    viewModel = ReservationViewModel(),
                    onReserve = {
                        homeViewModel.changeIndex(0)
                    }
                )
                4 -> ProfileContent(
                    littleLemonUser = littleLemonUser,
                    onLogout = { onLogout() },
                    navController = navController,
                    isDarkTheme = isDarkTheme,
                    onThemeChange = onThemeChange,
                    onLanguageChanged = onLanguageChanged,
                    currentLangCode = currentLangCode
                )
                else -> HomeContent(
                    navController = navController,
                    onSearchClicked = onSearchClicked,
                    categories = categories.value,
                    menuItems = localDishes.value,
                    onCategoryItemClicked = {name->
                        categoryName = if(categoryName.isNotEmpty() && categoryName==name){
                            ""
                        }else{
                            name
                        }
                    },
                    selectedCategory = categoryName,
                )
            }
        }
    }
}

@Composable
fun HomeBottomBar(
    bottomBarItems: List<HomeBottomBarItems>,
    onItemClick: (Int) -> Unit,
    activeIndex: Int?,
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