package com.example.littlelemoncoursera.ui.screens.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.littlelemoncoursera.R
import com.example.littlelemoncoursera.ui.screens.components.LittleLemonTextBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchItemPage(navController: NavController) {
    var keywordText by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            SearchPageAppBar(
                value = keywordText,
                onValueChange = {
                    keywordText=it
                },
                onBackClicked = {
                    if(navController.previousBackStackEntry!=null){
                        navController.navigateUp()
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it).fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Search Page")
        }
    }
}

@Composable
fun SearchPageAppBar(
    value: String, onValueChange: (String) -> Unit, onBackClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onBackClicked() }) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Back"
            )
        }
        LittleLemonTextBox(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            isError = false,
            isOutline = false,
        )
    }
}
