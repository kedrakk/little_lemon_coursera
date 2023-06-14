package com.example.littlelemon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.littlelemon.application.MenuNetworkData
import com.example.littlelemon.application.menuItemToDishItem
import com.example.littlelemon.data.local.URL
import com.example.littlelemon.data.local.entity.LocalDishItem
import com.example.littlelemon.data.local.local_db.LocalDishDatabase
import com.example.littlelemon.ui.LittleLemonMainPage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val client = HttpClient(Android) {
    install(ContentNegotiation) {
        json(contentType = ContentType("text", "plain"))
    }
}


lateinit var localDishDatabase:LocalDishDatabase

class MainActivity : ComponentActivity() {

    var dishItems = listOf<LocalDishItem>()

    private val dishDatabase  by lazy {
        Room.databaseBuilder(this,LocalDishDatabase::class.java,"localDish.db").build()
    }
    private suspend fun getNetworkData() {
         try {
            val responseData =
                client.get(URL.BASEURL).body<MenuNetworkData>()
            if(responseData.menuItemNetworkList.isNotEmpty()){
                withContext(Dispatchers.IO){
                    dishItems = menuItemToDishItem(responseData.menuItemNetworkList)
                    localDishDatabase.localDishDao().saveLocalDishes(dishItems)
                }
            }
        }catch (e:Exception){
            Log.e("Get Api Error",e.message.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        localDishDatabase = dishDatabase

        lifecycleScope.launch {
            getNetworkData()
        }

        setContent {
            LittleLemonMainPage()
        }
    }
}