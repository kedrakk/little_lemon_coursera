package com.example.littlelemoncoursera.data

import androidx.lifecycle.LiveData
import com.example.littlelemoncoursera.data.local.entity.LocalDishItem
import com.example.littlelemoncoursera.data.local.local_db.LocalDishDatabase

class DishDataRepository(private val localDishDatabase: LocalDishDatabase) {

    val allDishes:LiveData<List<LocalDishItem>> = localDishDatabase.localDishDao().getLocalDishes()
}