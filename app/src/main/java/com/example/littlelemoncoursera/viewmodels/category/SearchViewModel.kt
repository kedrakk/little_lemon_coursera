package com.example.littlelemoncoursera.viewmodels.category

import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.littlelemoncoursera.data.DishDataRepository
import com.example.littlelemoncoursera.data.local.local_db.LocalDishItem
import com.example.littlelemoncoursera.localDishDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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