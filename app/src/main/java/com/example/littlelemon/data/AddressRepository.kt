package com.example.littlelemon.data

import androidx.lifecycle.LiveData
import com.example.littlelemon.data.local.entity.AddressInformation
import com.example.littlelemon.data.local.local_db.LocalDishDatabase

class AddressRepository(private val localDishDatabase: LocalDishDatabase) {
    fun getAllAddressInformation():LiveData<List<AddressInformation>>{
        return localDishDatabase.localDishDao().getAllAddressInformation();
    }
}