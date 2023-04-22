package com.example.littlelemoncoursera.viewmodels.checkout

import androidx.lifecycle.ViewModel
import com.example.littlelemoncoursera.model.AddressInformation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CheckoutViewModel:ViewModel() {
    private val _uiState = MutableStateFlow(CheckoutUIState())
    val uiState: StateFlow<CheckoutUIState> = _uiState.asStateFlow()

    fun onAddressSelect(addressInformation: AddressInformation){
        _uiState.update {
            it.copy( selectedAddress = addressInformation)
        }
    }

    fun showOrHideAddForm(showOrHide:Boolean){
        _uiState.update {
            it.copy(showAddForm = showOrHide)
        }
    }
}