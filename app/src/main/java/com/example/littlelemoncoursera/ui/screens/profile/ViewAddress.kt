package com.example.littlelemoncoursera.ui.screens.profile

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.littlelemoncoursera.ui.screens.checkout.ChooseAddressInformation
import com.example.littlelemoncoursera.viewmodels.checkout.CheckoutViewModel

@Composable
fun ViewAddressPage(viewModel: CheckoutViewModel, navController: NavController) {
    ChooseAddressInformation(
        viewModel = viewModel,
        navController = navController,
        isFromCheckoutFlow = false,
    )
}