package com.example.littlelemoncoursera.ui.screens.reservation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.littlelemoncoursera.ui.screens.components.CommonAppBar
import com.example.littlelemoncoursera.ui.screens.components.QtySelectorComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationContent() {
    Scaffold(
        topBar = {
            CommonAppBar(
                title = "Reserve a table",
                onBackClicked = { /*TODO*/ },
                isBackIconContains = false
            )
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 15.dp)
        ) {
            Column{
                SelectGuestSizeComp()
            }
        }
    }
}

@Composable
fun SelectGuestSizeComp() {
    Column {
        Text(
            text = "Select the guest size",
            modifier = Modifier.padding(bottom = 10.dp)
        )
        QtySelectorComponent()
    }
}