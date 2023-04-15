package com.example.littlelemoncoursera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.littlelemoncoursera.ui.LittleLemonMainPage
import com.example.littlelemoncoursera.ui.theme.LittleLemonCourseraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonCourseraTheme {
                LittleLemonMainPage()
            }
        }
    }
}