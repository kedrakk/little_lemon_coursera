package com.example.littlelemoncoursera.viewmodels.main

import com.example.littlelemoncoursera.model.LittleLemonUser

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