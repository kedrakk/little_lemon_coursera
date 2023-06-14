package com.example.littlelemon.data.local.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import com.example.littlelemon.data.local.entity.AddressInformation
import com.example.littlelemon.data.local.entity.LocalDishItem


@Dao
interface MenuDao{
    @Query("SELECT * FROM localDishItem")
    fun getLocalDishes(): LiveData<List<LocalDishItem>>

    @Query("SELECT * FROM localDishItem where category = :categoryName")
    fun getLocalDishesByCategory(categoryName:String): LiveData<List<LocalDishItem>>

    @Query("SELECT * FROM localDishItem where title LIKE '%' || :keyword || '%'")
    fun searchLocalDishesByKeyword(keyword:String): LiveData<List<LocalDishItem>>

    @Query("SELECT * FROM localDishItem where id = :id")
    fun getLocalDishById(id:Int): LiveData<LocalDishItem>

    @Insert()
    fun saveLocalDishes(dishList: List<LocalDishItem>)

    @Query("SELECT DISTINCT category FROM localDishItem")
    fun getCategoryNames(): LiveData<List<String>>

    @Insert()
    fun addANewAddress(addressInformation: AddressInformation)

    @Query("SELECT * FROM addressInformation")
    fun getAllAddressInformation(): LiveData<List<AddressInformation>>

    @Update()
    fun updateAddressInformation(addressInformation: AddressInformation)
}

@Database(entities = [LocalDishItem::class,AddressInformation::class], version = 1)
abstract class LocalDishDatabase: RoomDatabase(){
    abstract fun localDishDao():MenuDao
}