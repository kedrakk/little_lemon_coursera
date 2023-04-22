package com.example.littlelemoncoursera.viewmodels.checkout

import com.example.littlelemoncoursera.model.AddressInformation
import com.example.littlelemoncoursera.model.AddressType

data class CheckoutUIState (
    val addressList:List<AddressInformation> = listOf(
        AddressInformation("123456","Khin Eaindra Kyaw","Yangon, Myanmar",AddressType.HOME,1),
        AddressInformation("234567","Martin","Yangon, Myanmar",AddressType.OTHERS,2)
    ),
    val selectedAddress:AddressInformation?=if(addressList.isNotEmpty()) addressList.first() else null,
    val showAddForm:Boolean=false,
)