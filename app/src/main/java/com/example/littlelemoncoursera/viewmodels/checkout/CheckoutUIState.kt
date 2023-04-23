package com.example.littlelemoncoursera.viewmodels.checkout

import com.example.littlelemoncoursera.data.local.PaymentMethodList
import com.example.littlelemoncoursera.model.AddressInformation
import com.example.littlelemoncoursera.model.AddressType
import com.example.littlelemoncoursera.model.Dish
import com.example.littlelemoncoursera.model.PaymentMethod

data class CheckoutUIState (
    val addressList:List<AddressInformation> = listOf(
        AddressInformation("123456","Khin Eaindra Kyaw","Yangon, Myanmar",AddressType.HOME,1),
        AddressInformation("234567","Martin","Yangon, Myanmar",AddressType.OTHERS,2)
    ),
    val selectedAddress:AddressInformation?=if(addressList.isNotEmpty()) addressList.first() else null,
    val showAddForm:Boolean=false,
    val paymentList:List<PaymentMethod> = PaymentMethodList.allPaymentMethods,
    val selectedPaymentMethod: PaymentMethod? = null,
    val selectedItems:List<Dish> = listOf(),
    val totalPrice:Int=0,
)