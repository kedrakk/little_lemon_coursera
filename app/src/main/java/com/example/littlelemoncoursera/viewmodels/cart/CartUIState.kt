package com.example.littlelemoncoursera.viewmodels.cart

import com.example.littlelemoncoursera.cartItems
import com.example.littlelemoncoursera.model.CartItem

data class CartUIState(val cartItemList:MutableList<CartItem> = cartItems)