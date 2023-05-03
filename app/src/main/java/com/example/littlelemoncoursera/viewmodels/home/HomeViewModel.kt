package com.example.littlelemoncoursera.viewmodels.home

import androidx.lifecycle.ViewModel
import com.example.littlelemoncoursera.data.local.HomeBottomBarData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel:ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()
    val bottomBarItems = HomeBottomBarData.homeBottomBarItems


    fun changeIndex(newIndex:Int){
        _uiState.update {
            it.copy(selectedIndex = newIndex)
        }
    }
}