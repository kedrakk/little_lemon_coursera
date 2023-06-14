package com.example.littlelemon.ui.screens.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemoncoursera.R
import com.example.littlelemon.data.local.entity.LocalDishItem
import com.example.littlelemon.navigation.Routes
import com.example.littlelemon.ui.screens.components.LittleLemonTextBox
import com.example.littlelemon.viewmodels.category.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchItemPage(navController: NavController, searchViewModel: SearchViewModel) {
    var keywordText by remember {
        mutableStateOf("")
    }
    val localDishes =
        searchViewModel.searchLocalDishes(keyword = keywordText).observeAsState(initial = emptyList())

    Scaffold(
        topBar = {
            SearchPageAppBar(
                value = keywordText,
                onValueChange = { text ->
                    keywordText = text
                },
                onBackClicked = {
                    if (navController.previousBackStackEntry != null) {
                        navController.navigateUp()
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            SearchedItemList(
                dishesList = localDishes.value,
                onDishItemClicked = {dishId->
                    navController.navigateUp()
                    navController.navigate("${Routes.DISH_DETAIL.name}/$dishId")
                },

                )
        }
    }
}

@Composable
fun SearchPageAppBar(
    value: String, onValueChange: (String) -> Unit, onBackClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onBackClicked() }) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = stringResource(R.string.back),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
            )
        }
        LittleLemonTextBox(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            isError = false,
            isOutline = false,
            placeholderText = stringResource(R.string.enter_search_phrase),
        )
    }
}

@Composable
fun SearchedItemList(dishesList: List<LocalDishItem>, onDishItemClicked: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 10.dp)
    ) {
        items(
            dishesList.size,
            itemContent = {
                Text(
                    text = dishesList[it].title,
                    modifier = Modifier
                        .padding(
                            horizontal = 20.dp, vertical = 15.dp
                        )
                        .clickable {
                            onDishItemClicked(dishesList[it].id)
                        }
                )
            }
        )
    }
}
