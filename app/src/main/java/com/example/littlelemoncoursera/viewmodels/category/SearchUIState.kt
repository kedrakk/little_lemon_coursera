package com.example.littlelemoncoursera.viewmodels.category

import com.example.littlelemoncoursera.data.local.local_db.LocalDishItem

data class SearchUIState(
    val filteredMenuItemsList:List<LocalDishItem> = listOf()
)