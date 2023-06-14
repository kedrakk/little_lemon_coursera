package com.example.littlelemon.data

import androidx.lifecycle.LiveData
import com.example.littlelemon.data.local.entity.LocalDishItem
import com.example.littlelemon.data.local.local_db.LocalDishDatabase

class DishDataRepository(private val localDishDatabase: LocalDishDatabase) {

    val allDishes:LiveData<List<LocalDishItem>> = localDishDatabase.localDishDao().getLocalDishes()
}