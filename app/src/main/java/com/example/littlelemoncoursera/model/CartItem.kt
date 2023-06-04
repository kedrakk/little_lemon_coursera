package com.example.littlelemoncoursera.model

import com.example.littlelemoncoursera.data.local.entity.LocalDishItem

data class CartItem(
    val localDishItem: LocalDishItem,
    var quantity:Int
)
