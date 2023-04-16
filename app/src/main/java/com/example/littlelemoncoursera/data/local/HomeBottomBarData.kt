package com.example.littlelemoncoursera.data.local

import com.example.littlelemoncoursera.R
import com.example.littlelemoncoursera.model.HomeBottomBarItems

object HomeBottomBarData {
    val homeBottomBarItems = listOf<HomeBottomBarItems>(
        HomeBottomBarItems(label = "Home", index = 0, iconData = R.drawable.baseline_home_24),
        HomeBottomBarItems(label = "Category", index = 1,iconData = R.drawable.baseline_category_24),
        HomeBottomBarItems(label = "Cart", index = 2,iconData = R.drawable.baseline_shopping_cart_24),
        HomeBottomBarItems(label = "RSVN", index = 3,iconData = R.drawable.baseline_calendar_month_24),
        HomeBottomBarItems(label = "Profile", index = 4,iconData = R.drawable.baseline_account_circle_24),
    )
}