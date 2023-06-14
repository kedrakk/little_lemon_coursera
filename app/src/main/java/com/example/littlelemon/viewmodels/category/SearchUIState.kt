package com.example.littlelemon.viewmodels.category

import com.example.littlelemon.data.local.entity.LocalDishItem

data class SearchUIState(
    val filteredMenuItemsList:List<LocalDishItem> = listOf()
)