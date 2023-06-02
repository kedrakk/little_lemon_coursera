package com.example.littlelemoncoursera.viewmodels.cart

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel:ViewModel() {
    private val _uiState = MutableStateFlow(CartUIState())
    val uiState: StateFlow<CartUIState> = _uiState.asStateFlow()
}