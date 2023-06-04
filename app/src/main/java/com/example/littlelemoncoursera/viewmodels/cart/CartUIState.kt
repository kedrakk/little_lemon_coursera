package com.example.littlelemoncoursera.viewmodels.cart

import com.example.littlelemoncoursera.cartItems
import com.example.littlelemoncoursera.model.CartItem

data class CartUIState(
    val selectedCartItems: MutableList<CartItem> = cartItems,
    val itemCount:Int = selectedCartItems.size,
    val emptyText: String = if (selectedCartItems.isEmpty()) "Cart is Empty" else ""
)
