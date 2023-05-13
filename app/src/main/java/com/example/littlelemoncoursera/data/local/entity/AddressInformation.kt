package com.example.littlelemoncoursera.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.littlelemoncoursera.model.AddressType

@Entity
data class AddressInformation(
    @PrimaryKey(autoGenerate = true) val addressId:Int?,
    val phoneNumber: String,
    val receiverName: String,
    val addressDetailInformation: String,
    val addressType: AddressType
)
