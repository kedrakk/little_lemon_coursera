package com.example.littlelemon.application

import com.example.littlelemon.data.local.entity.LocalDishItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuNetworkData(
    @SerialName("menu") val menuItemNetworkList:List<MenuItemNetwork>
    )

@Serializable
data class MenuItemNetwork(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("price") val price: String,
    @SerialName("image") val image: String,
    @SerialName("category") val category: String,
)

fun menuItemToDishItem(menuItems:List<MenuItemNetwork>):List<LocalDishItem>{
    val localDishItems:MutableList<LocalDishItem> = mutableListOf()
    for (menuItem in menuItems){
        localDishItems.add(
            LocalDishItem(
                id = menuItem.id,
                title = menuItem.title,
                description = menuItem.description,
                price = menuItem.price,
                image = menuItem.image,
                category = menuItem.category,
            )
        )
    }
    return localDishItems
}