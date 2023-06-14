package com.example.littlelemon.viewmodels.cart

import com.example.littlelemon.cartItems
import com.example.littlelemon.model.CartItem

data class CartUIState(
    val selectedCartItems: MutableList<CartItem> = cartItems,
    val itemCount:Int = selectedCartItems.size,
    val emptyText: String = if (selectedCartItems.isEmpty()) "Cart is Empty" else ""
)
