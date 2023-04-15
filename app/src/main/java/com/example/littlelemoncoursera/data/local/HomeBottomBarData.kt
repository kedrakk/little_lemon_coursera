package com.example.littlelemoncoursera.data.local

import com.example.littlelemoncoursera.model.HomeBottomBarItems

object HomeBottomBarData {
    val homeBottomBarItems = listOf<HomeBottomBarItems>(
        HomeBottomBarItems(label = "Home", index = 0),
        HomeBottomBarItems(label = "Category", index = 1),
        HomeBottomBarItems(label = "Cart", index = 2),
        HomeBottomBarItems(label = "Reservation", index = 3),
        HomeBottomBarItems(label = "Profile", index = 4),
    )
}