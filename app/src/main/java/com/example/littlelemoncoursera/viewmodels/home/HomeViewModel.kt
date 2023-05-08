package com.example.littlelemoncoursera.viewmodels.home

import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemoncoursera.data.local.HomeBottomBarData
import com.example.littlelemoncoursera.data.local.local_db.LocalDishItem
import com.example.littlelemoncoursera.localDishDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()
    val bottomBarItems = HomeBottomBarData.homeBottomBarItems

    fun getLocalDishes(categoryName:String):LiveData<List<LocalDishItem>> {
        return if(categoryName.isEmpty()){
            localDishDatabase.localDishDao().getLocalDishes()
        }else{
            localDishDatabase.localDishDao().getLocalDishesByCategory(categoryName = categoryName)
        }
    }

    fun getAllCategories():LiveData<List<String>>{
        return localDishDatabase.localDishDao().getCategoryNames()
    }

    fun changeIndex(newIndex: Int) {
        _uiState.update {
            it.copy(selectedIndex = newIndex)
        }
    }
}