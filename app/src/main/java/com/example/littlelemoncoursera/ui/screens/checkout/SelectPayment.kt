package com.example.littlelemoncoursera.ui.screens.checkout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.littlelemoncoursera.ui.screens.components.CommonAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectPaymentPage(navController: NavController) {
    Scaffold(
        topBar = {
            CommonAppBar(title = "Select Payment") {
                if (navController.previousBackStackEntry != null) {
                    navController.navigateUp()
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Text(text = "Select Payment")
        }
    }
}