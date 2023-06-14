package com.example.littlelemon.viewmodels.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.littlelemon.data.local.entity.AddressInformation
import com.example.littlelemon.data.local.entity.LocalDishItem
import com.example.littlelemon.localDishDatabase
import com.example.littlelemon.model.PaymentMethod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class CheckoutViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(CheckoutUIState())
    val uiState: StateFlow<CheckoutUIState> = _uiState.asStateFlow()

    fun setDefaultQty(){
        _uiState.update {
            it.copy(selectedQty = 1)
        }
    }

    fun increaseQty(prevQty: Int, originalPrice: Int) {
        val newQty = prevQty + 1
        _uiState.update {
            it.copy(
                selectedQty = newQty, totalPrice = originalPrice * newQty
            )
        }
    }

    fun decreaseQty(prevQty: Int, originalPrice: Int) {
        val newQty = if (prevQty > 1) prevQty - 1 else prevQty
        _uiState.update {
            it.copy(
                selectedQty = newQty, totalPrice = originalPrice * newQty
            )
        }
    }

    fun setItemAndPrice(newItemsList: List<LocalDishItem>, newPrice: Int) {
        _uiState.update {
            it.copy(selectedItems = newItemsList, totalPrice = newPrice)
        }
    }

    fun getAllAddresses(): LiveData<List<AddressInformation>> {
        return localDishDatabase.localDishDao().getAllAddressInformation();
    }

    suspend fun addANewAddress(addressInformation: AddressInformation) =
        withContext(Dispatchers.IO) {
            localDishDatabase.localDishDao().addANewAddress(addressInformation);
        }

    suspend fun updateAddress(addressInformation: AddressInformation) =
        withContext(Dispatchers.IO) {
            localDishDatabase.localDishDao().updateAddressInformation(addressInformation);
        }

    fun onAddressSelect(addressInformation: AddressInformation) {
        _uiState.update {
            it.copy(selectedAddress = addressInformation)
        }
    }

    fun onPaymentSelect(paymentMethod: PaymentMethod) {
        _uiState.update {
            it.copy(selectedPaymentMethod = paymentMethod)
        }
    }
}