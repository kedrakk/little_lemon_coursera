package com.example.littlelemoncoursera.ui.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.littlelemoncoursera.model.CartItem
import com.example.littlelemoncoursera.ui.screens.components.CommonAppBar
import com.example.littlelemoncoursera.ui.screens.components.NetworkImageLoader
import com.example.littlelemoncoursera.ui.screens.dish.SelectDishQty
import com.example.littlelemoncoursera.viewmodels.cart.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(cartViewModel: CartViewModel) {
    val uiState by cartViewModel.uiState.collectAsState()
   return Scaffold(
        topBar = {
            CommonAppBar(
                title = "Cart Items",
                onBackClicked = { /*TODO*/ },
                isBackIconContains = false
            )
        }
    ) {
        LazyColumn(
            content = {
                items(uiState.cartItemList){item->
                    CartItemComponent(dishItem = item)
                }
            },
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 15.dp)
        )
    }
}

@Composable
fun CartItemComponent(dishItem: CartItem) {
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
                        text = "$" + dishItem.localDishItem.price,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    SelectDishQty(
                        currentQty = dishItem.quantity.toString(),
                        modifier = Modifier.padding(1.dp),
                        onIncreased = {

                        },
                        onDecreased = {

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