package com.example.littlelemoncoursera.viewmodels.category

import com.example.littlelemoncoursera.data.local.entity.LocalDishItem

data class SearchUIState(
    val filteredMenuItemsList:List<LocalDishItem> = listOf()
)