package com.example.littlelemon.viewmodels.main

import com.example.littlelemon.model.LittleLemonUser

data class LittleLemonMainUIState(
    val littleLemonUser: LittleLemonUser = LittleLemonUser(
        "",
        "",
        "",
        ""
    ),
    val isLoading: Boolean = true,
    val isDarkMode:Boolean = true,
    val langCode:String = "en"
)