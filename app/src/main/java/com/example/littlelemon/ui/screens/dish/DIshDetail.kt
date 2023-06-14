package com.example.littlelemon.ui.screens.dish

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.littlelemoncoursera.R
import com.example.littlelemon.data.local.entity.LocalDishItem
import com.example.littlelemon.localDishDatabase
import com.example.littlelemon.model.CartItem
import com.example.littlelemon.navigation.Routes
import com.example.littlelemon.ui.screens.components.ActionButton
import com.example.littlelemon.ui.screens.components.CommonAppBar
import com.example.littlelemon.ui.screens.components.EmptyPageComponent
import com.example.littlelemon.ui.screens.components.NetworkImageLoader
import com.example.littlelemon.viewmodels.checkout.CheckoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishDetailPage(
    dishId: Int,
    navController: NavController,
    checkoutViewModel: CheckoutViewModel,
    onOrderNow: (List<LocalDishItem>, Int) -> Unit,
    onAddToCart: (CartItem) -> Unit,
) {
    val uiState by checkoutViewModel.uiState.collectAsState()
    val dish = localDishDatabase.localDishDao().getLocalDishById(dishId).observeAsState().value
        ?: return EmptyPageComponent(message = stringResource(R.string.empty_dish))

    return Scaffold(
        topBar = {
            CommonAppBar(
                title = dish.title,
                onBackClicked = {
                    checkoutViewModel.setDefaultQty()
                    if (navController.previousBackStackEntry != null) {
                        navController.navigateUp()
                    }
                }
            )
        },
        bottomBar = {
            Column() {
                ActionButton(
                    onClick = {
                        val dishList: MutableList<LocalDishItem> = mutableListOf()
                        var totalPrice = 0
                        for (i in 1..uiState.selectedQty) {
                            dishList.add(dish)
                            totalPrice += dish.price.toInt()
                        }
                        onOrderNow(dishList, totalPrice)
                        navController.navigate(Routes.ADDRESS_CONFIRM.name)
                    },
                    label = stringResource(R.string.order_now),
                    verticalPadding = 10
                )
                ActionButton(
                    onClick = {
                        onAddToCart(
                            CartItem(
                                localDishItem = dish,
                                quantity = uiState.selectedQty
                            )
                        )
                        checkoutViewModel.setDefaultQty()
                    },
                    label = stringResource(R.string.add_to_cart),
                    isOutline = true,
                    verticalPadding = 10
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            item {
                NetworkImageLoader(
                    imageURL = dish.image,
                    title = dish.title,
                    modifier = Modifier.padding(10.dp)
                )
                CategoryIndicator(name = dish.category)
                Text(
                    text = dish.description,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SelectDishQty(
                        currentQty = uiState.selectedQty.toString(),
                        modifier = Modifier.weight(1F).padding(horizontal = 15.dp, vertical = 20.dp),
                        onIncreased = {
                            checkoutViewModel.increaseQty(
                                prevQty = uiState.selectedQty,
                                originalPrice = dish.price.toInt()
                            )
                        },
                        onDecreased = {
                            checkoutViewModel.decreaseQty(
                                prevQty = uiState.selectedQty,
                                originalPrice = dish.price.toInt()
                            )
                        }
                    )
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .weight(1F)
                            .padding(horizontal = 15.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.total_price),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = if (uiState.selectedQty > 1) {
                                "$" + uiState.totalPrice.toString()
                            } else {
                                "$" + dish.price
                            },
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 25.sp
                            )
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun CategoryIndicator(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_fastfood_24),
            contentDescription = name,
            modifier = Modifier.padding(horizontal = 10.dp),
            tint = MaterialTheme.colorScheme.tertiary
        )
        Text(
            text = name.uppercase(),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold
            ),
        )
    }
}

@Composable
fun SelectDishQty(
    currentQty: String,
    modifier: Modifier,
    onIncreased: () -> Unit,
    onDecreased: () -> Unit,
    iconSize: Dp = 24.dp,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    iconModifier: Modifier = Modifier.padding(0.dp)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.border(1.dp, Color.Gray, CutCornerShape(2.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = { onIncreased() },
            modifier = iconModifier
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_24),
                contentDescription = "Add",
                modifier = Modifier.size(iconSize)
            )
        }
        Text(
            text = currentQty,
            style = textStyle.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        IconButton(
            onClick = { onDecreased() },
            modifier = iconModifier
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_remove_24),
                contentDescription = "Remove",
                modifier = Modifier.size(iconSize)
            )
        }
    }
}