package com.example.littlelemoncoursera.viewmodels.checkout

import com.example.littlelemoncoursera.data.local.PaymentMethodList
import com.example.littlelemoncoursera.data.local.entity.AddressInformation
import com.example.littlelemoncoursera.data.local.entity.LocalDishItem
import com.example.littlelemoncoursera.model.PaymentMethod

data class CheckoutUIState(
    val selectedQty: Int = 1,
    val selectedAddress: AddressInformation? = null,
    val paymentList: List<PaymentMethod> = PaymentMethodList.allPaymentMethods,
    val selectedPaymentMethod: PaymentMethod? = null,
    val selectedItems: List<LocalDishItem> = listOf(),
    val totalPrice: Int = 0,
)