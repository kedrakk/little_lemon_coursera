package com.example.littlelemon.ui.screens.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.littlelemon.data.local.entity.LocalDishItem
import com.example.littlelemon.navigation.Routes
import com.example.littlelemon.ui.screens.home.DishesList
import com.example.littlelemon.ui.screens.home.OrderForDelivery

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryContent(
    navController: NavController,
    categories: List<String>,
    menuItems: List<LocalDishItem>,
    onCategoryItemClicked: (String) -> Unit,
    selectedCategory: String,
) {
    Scaffold{
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            OrderForDelivery(
                categories = categories,
                onCategoryItemClicked = { categoryItem ->
                    onCategoryItemClicked(categoryItem)
                },
                selectedCategory = selectedCategory
            )
            Divider()
            DishesList(
                dishesList = menuItems,
                onDishItemClicked = { dishId ->
                    navController.navigate("${Routes.DISH_DETAIL.name}/$dishId")
                }
            )
        }
    }

}