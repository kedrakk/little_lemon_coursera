package com.example.littlelemon.ui.screens.cart

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.littlelemon.data.local.entity.LocalDishItem
import com.example.littlelemon.model.CartItem
import com.example.littlelemon.ui.screens.components.ActionButton
import com.example.littlelemon.ui.screens.components.CommonAppBar
import com.example.littlelemon.ui.screens.components.NetworkImageLoader
import com.example.littlelemon.ui.screens.dish.SelectDishQty
import com.example.littlelemon.viewmodels.cart.CartViewModel
import com.example.littlelemoncoursera.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(cartViewModel: CartViewModel,onCheckoutFromCart:(List<LocalDishItem>, Int)->Unit) {
    val cartUiState by cartViewModel.uiState.collectAsState()
    val context = LocalContext.current

    return Scaffold(
        topBar = {
            CommonAppBar(
                title = stringResource(R.string.cart_items),
                onBackClicked = { /*TODO*/ },
                isBackIconContains = false
            )
        },
        bottomBar = {
            if (cartUiState.selectedCartItems.isNotEmpty()) {
                ActionButton(
                    onClick = {
                        val selectedDishItems = cartViewModel.convertDishItems(cartUiState.selectedCartItems)
                        val total = cartViewModel.getTotal(cartUiState.selectedCartItems)
                        onCheckoutFromCart(selectedDishItems,total)
                    },
                    label = stringResource(R.string.checkout_now),
                    verticalPadding = 10
                )
            }
        }
    ) {
        if (cartUiState.selectedCartItems.isEmpty()) {
            Box(
                modifier = Modifier.padding(it).fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = cartUiState.emptyText, color = MaterialTheme.colorScheme.secondary)
            }
        } else {
            LazyColumn(
                content = {
                    items(cartUiState.selectedCartItems) { item ->
                        CartItemComponent(
                            dishItem = item,
                            onIncreaseItemQty = {
                                if (item.quantity < 5) {
                                    cartViewModel.changeQTY(
                                        item = item,
                                        isIncrease = true,
                                        itemCount = cartUiState.itemCount
                                    )
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please select at most five items",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            onDecreaseItemQty = {
                                if (item.quantity > 1) {
                                    cartViewModel.changeQTY(
                                        item = item,
                                        isIncrease = false,
                                        itemCount = cartUiState.itemCount
                                    )
                                } else {
                                    cartViewModel.removeItemFromCart(
                                        item,
                                    )
                                }
                            },
                            itemPrice = item.localDishItem.price.toInt() * item.quantity
                        )
                    }
                },
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 15.dp)
            )
        }
    }
}

@Composable
fun CartItemComponent(
    dishItem: CartItem,
    onIncreaseItemQty: () -> Unit,
    onDecreaseItemQty: () -> Unit,
    itemPrice: Int,
) {
    Column(modifier = Modifier.padding(top = 10.dp)) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            NetworkImageLoader(
                imageURL = dishItem.localDishItem.image,
                title = dishItem.localDishItem.title,
                modifier = Modifier.weight(1F)
            )
            Column(
                modifier = Modifier
                    .weight(2F)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = dishItem.localDishItem.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = dishItem.localDishItem.description,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 5.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$$itemPrice",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    SelectDishQty(
                        currentQty = dishItem.quantity.toString(),
                        modifier = Modifier.padding(1.dp),
                        onIncreased = {
                            onIncreaseItemQty()
                        },
                        onDecreased = {
                            onDecreaseItemQty()
                        },
                        iconSize = 18.dp,
                        iconModifier = Modifier.size(35.dp),
                        textStyle = MaterialTheme.typography.titleMedium
                    )
                }

            }
        }
        Divider(
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}