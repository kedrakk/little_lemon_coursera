package com.example.littlelemon.model

import com.example.littlelemon.data.local.entity.LocalDishItem

data class CartItem(
    val localDishItem: LocalDishItem,
    var quantity:Int
)
