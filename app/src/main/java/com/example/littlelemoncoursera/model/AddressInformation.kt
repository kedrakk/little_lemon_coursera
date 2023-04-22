package com.example.littlelemoncoursera.model

data class AddressInformation(
    val phoneNumber: String,
    val receiverName: String,
    val addressDetailInformation: String,
    val addressType: AddressType,
    val addressId:Int
)
