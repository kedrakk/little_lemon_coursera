package com.example.littlelemoncoursera.ui.screens.dish

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemoncoursera.R
import com.example.littlelemoncoursera.data.local.TestDishData
import com.example.littlelemoncoursera.ui.screens.components.NetworkImageLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishDetailPage(dishId: Int,navController:NavController) {
    val dish= TestDishData.TestDishDataList.first {
        it.id == dishId
    }
    return Scaffold(
        topBar = {
            DishDetailAppBar(
                title = dish.title,
                onBackClicked = {
                    if(navController.previousBackStackEntry!=null){
                        navController.navigateUp()
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ){
            item {
                NetworkImageLoader(
                    imageURL = dish.image,
                    title = dish.title,
                    modifier = Modifier.padding(10.dp)
                )
                CategoryIndicator(name = dish.category)
                Text(
                    text = dish.description,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
                )
            }
        }
    }
}

@Composable
fun CategoryIndicator(name:String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
       Icon(
           painter = painterResource(id = R.drawable.baseline_fastfood_24),
           contentDescription = name,
           modifier = Modifier.padding(horizontal = 10.dp),
           tint = MaterialTheme.colorScheme.tertiary
       )
       Text(
           text = name.uppercase(),
           style = MaterialTheme.typography.bodyMedium.copy(
               color = MaterialTheme.colorScheme.tertiary,
               fontWeight = FontWeight.Bold
           ),
       )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishDetailAppBar(title:String,onBackClicked:()->Unit,) {
    TopAppBar(
        title = {
                Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = { onBackClicked() }) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "Back"
                )
            }
        }
    )
}

@Preview
@Composable
fun DishPageDetailPreview() {
    DishDetailPage(dishId = 1, navController = rememberNavController())
}