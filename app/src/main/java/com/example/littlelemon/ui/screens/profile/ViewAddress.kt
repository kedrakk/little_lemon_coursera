package com.example.littlelemon.ui.screens.profile

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.littlelemon.ui.screens.checkout.ChooseAddressInformation
import com.example.littlelemon.viewmodels.checkout.CheckoutViewModel

@Composable
fun ViewAddressPage(viewModel: CheckoutViewModel, navController: NavController) {
    ChooseAddressInformation(
        viewModel = viewModel,
        navController = navController,
        isFromCheckoutFlow = false,
    )
}