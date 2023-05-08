package com.example.littlelemoncoursera.data.local.local_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
data class LocalDishItem(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String,
)

@Dao
interface MenuDao{
    @Query("SELECT * FROM localDishItem")
    fun getLocalDishes(): LiveData<List<LocalDishItem>>

    @Query("SELECT * FROM localDishItem where category = :categoryName")
    fun getLocalDishesByCategory(categoryName:String): LiveData<List<LocalDishItem>>

    @Query("SELECT * FROM localDishItem where id = :id")
    fun getLocalDishById(id:Int): LiveData<LocalDishItem>

    @Insert()
    fun saveLocalDishes(dishList: List<LocalDishItem>)

    @Query("SELECT DISTINCT category FROM localDishItem")
    fun getCategoryNames(): LiveData<List<String>>
}

@Database(entities = [LocalDishItem::class], version = 1)
abstract class LocalDishDatabase: RoomDatabase(){
    abstract fun localDishDao():MenuDao
}