package com.example.littlelemon.model

data class LittleLemonUser(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
)

data class MyAppTheme(
    val isDarkMode: Boolean
)

data class LittleLemonUserWithAppTheme(
    val littleLemonUser: LittleLemonUser,
    val myAppTheme: MyAppTheme,
    val langCode:String
)