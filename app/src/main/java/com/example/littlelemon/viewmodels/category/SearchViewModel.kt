package com.example.littlelemon.viewmodels.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.littlelemon.data.local.entity.LocalDishItem
import com.example.littlelemon.localDishDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel (allDishes:List<LocalDishItem>):ViewModel() {
    private val _uiState = MutableStateFlow(SearchUIState())
    val uiState: StateFlow<SearchUIState> = _uiState.asStateFlow()

    fun searchLocalDishes(keyword: String):LiveData<List<LocalDishItem>> {
        return if(keyword.isEmpty()){
            localDishDatabase.localDishDao().getLocalDishes()
        }else{
            localDishDatabase.localDishDao().searchLocalDishesByKeyword(keyword)
        }
    }

}