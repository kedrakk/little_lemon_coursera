package com.example.littlelemon.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemoncoursera.R
import com.example.littlelemon.data.local.entity.LocalDishItem
import com.example.littlelemon.navigation.Routes
import com.example.littlelemon.ui.screens.components.HomeAppBar
import com.example.littlelemon.ui.screens.components.NetworkImageLoader
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    navController: NavController,
    onSearchClicked: (dishDataList: List<LocalDishItem>) -> Unit,
    categories: List<String>,
    menuItems: List<LocalDishItem>,
    onCategoryItemClicked: (String) -> Unit,
    selectedCategory: String,
) {

    Scaffold(
        topBar = {
            HomeAppBar(
                onNavigateToSearchPage = {
                    onSearchClicked(menuItems)
                }
            )
        }
    ) { it ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            HomeHero(
                onSearch = {
                    onSearchClicked(menuItems)
                },
                label = stringResource(R.string.search_menu)
            )
            OrderForDelivery(
                categories = categories,
                onCategoryItemClicked = { categoryName ->
                    onCategoryItemClicked(categoryName)
                },
                selectedCategory = selectedCategory,
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

@Composable
fun HomeHero(onSearch: () -> Unit,label: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(color = MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1F)
                    .padding(horizontal = 15.dp)
            ) {
                Text(
                    text = "Little Lemon",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Text(
                    text = "Chicago",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = stringResource(id = R.string.hero_desc),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(top = 15.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Little Lemon",
                modifier = Modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(15))
                    .weight(1F)
                    .padding(horizontal = 15.dp),
                contentScale = ContentScale.Crop

            )
        }
        SearchBoxOnHero(onSearch = onSearch,label)
    }
}

@Composable
fun SearchBoxOnHero(onSearch: () -> Unit,label: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .clip(RoundedCornerShape(15))
            .clickable {
                onSearch()
            }
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = "Search",
                modifier = Modifier.padding(horizontal = 5.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
fun OrderForDelivery(
    categories: List<String>,
    onCategoryItemClicked: (String) -> Unit,
    selectedCategory: String
) {
    Column(
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.order_for_delivery).uppercase(),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
        )
        LazyRow(
            modifier = Modifier.padding(vertical = 20.dp)
        ) {
            items(
                count = categories.size,
                itemContent = {
                    CategoryPill(
                        label = categories[it],
                        onCategoryItemClicked = { itemName -> onCategoryItemClicked(itemName) },
                        isSelected = categories[it] == selectedCategory
                    )
                }
            )
        }
    }
}

@Composable
fun CategoryPill(
    label: String,
    onCategoryItemClicked: (String) -> Unit,
    isSelected: Boolean,
    paddingModifier: Modifier = Modifier
        .padding(end = 10.dp),
    roundPercentage:Int = 50
) {
    Surface(
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
            alpha = .3F
        ),
        modifier = paddingModifier
            .clip(RoundedCornerShape(roundPercentage))
            .clickable {
                onCategoryItemClicked(label)
            },
    ) {
        Text(
            text = label.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        )
    }
}

@Composable
fun DishesList(dishesList: List<LocalDishItem>, onDishItemClicked: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 10.dp)
    ) {
        items(
            dishesList.size,
            itemContent = {
                DishItem(
                    dishItem = dishesList[it],
                    onDishClicked = {
                        onDishItemClicked(dishesList[it].id)
                    }
                )
            }
        )
    }
}

@Composable
fun DishItem(dishItem: LocalDishItem, onDishClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .clickable {
                onDishClicked()
            },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(2F)
            ) {
                Text(
                    text = dishItem.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = dishItem.description,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 5.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "$" + dishItem.price,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }
            NetworkImageLoader(
                imageURL = dishItem.image,
                title = dishItem.title,
                modifier = Modifier.weight(1F)
            )
        }
        Divider(
            modifier = Modifier.padding(top = 15.dp)
        )
    }
}

