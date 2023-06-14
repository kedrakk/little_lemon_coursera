package com.example.littlelemon.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalDishItem(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String,
)