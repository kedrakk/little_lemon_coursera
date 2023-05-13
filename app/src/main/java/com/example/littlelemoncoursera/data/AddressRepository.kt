package com.example.littlelemoncoursera.data

import androidx.lifecycle.LiveData
import com.example.littlelemoncoursera.data.local.entity.AddressInformation
import com.example.littlelemoncoursera.data.local.local_db.LocalDishDatabase

class AddressRepository(private val localDishDatabase: LocalDishDatabase) {
    fun getAllAddressInformation():LiveData<List<AddressInformation>>{
        return localDishDatabase.localDishDao().getAllAddressInformation();
    }
}