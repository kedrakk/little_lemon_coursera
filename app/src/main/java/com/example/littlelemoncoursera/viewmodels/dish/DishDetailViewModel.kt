package com.example.littlelemoncoursera.viewmodels.dish

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DishDetailViewModel:ViewModel() {
    private val _uiState = MutableStateFlow(DishDetailUIState())
    val uiState: StateFlow<DishDetailUIState> = _uiState.asStateFlow()

    fun increaseQty(prevQty:Int,originalPrice:Int){
        val newQty=prevQty+1
        _uiState.update {
            it.copy(
                selectedQty = newQty, totalPrice =originalPrice*newQty
            )
        }
    }

    fun decreaseQty(prevQty:Int,originalPrice:Int){
        val newQty=if(prevQty>1) prevQty-1 else prevQty
        _uiState.update {
            it.copy(
                selectedQty = newQty, totalPrice =originalPrice*newQty
            )
        }
    }
}