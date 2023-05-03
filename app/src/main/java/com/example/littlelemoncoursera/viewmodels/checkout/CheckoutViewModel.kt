package com.example.littlelemoncoursera.viewmodels.checkout

import androidx.lifecycle.ViewModel
import com.example.littlelemoncoursera.data.local.local_db.LocalDishItem
import com.example.littlelemoncoursera.model.AddressInformation
import com.example.littlelemoncoursera.model.PaymentMethod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CheckoutViewModel:ViewModel() {
    private val _uiState = MutableStateFlow(CheckoutUIState())
    val uiState: StateFlow<CheckoutUIState> = _uiState.asStateFlow()

    fun setItemAndPrice(newItemsList:List<LocalDishItem>,newPrice:Int){
        _uiState.update {
            it.copy(selectedItems = newItemsList, totalPrice = newPrice)
        }
    }

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

    fun onPaymentSelect(paymentMethod: PaymentMethod){
        _uiState.update {
            it.copy(selectedPaymentMethod = paymentMethod)
        }
    }
}