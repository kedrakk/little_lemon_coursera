package com.example.littlelemon.ui.screens.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemoncoursera.R
import com.example.littlelemon.model.PaymentMethod
import com.example.littlelemon.navigation.Routes
import com.example.littlelemon.ui.screens.components.ActionButton
import com.example.littlelemon.ui.screens.components.CommonAppBar
import com.example.littlelemon.viewmodels.checkout.CheckoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectPaymentPage(navController: NavController, viewModel: CheckoutViewModel) {
    val checkoutUIState = viewModel.uiState.collectAsState().value
    Scaffold(
        topBar = {
            CommonAppBar(
                title = stringResource(R.string.select_payment),
                onBackClicked = {
                    if (navController.previousBackStackEntry != null) {
                        navController.navigateUp()
                    }
                },
            )
        },
        bottomBar = {
            if(checkoutUIState.selectedPaymentMethod!=null)
                ActionButton(
                    onClick = { navController.navigate(Routes.CHECKOUT_REVIEW.name) },
                    label = stringResource(R.string.continue_label)
                )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            for (i in 0 until checkoutUIState.paymentList.size) {
                PaymentCard(
                    paymentMethod = checkoutUIState.paymentList[i],
                    isPaymentSelected = checkoutUIState.paymentList[i] == checkoutUIState.selectedPaymentMethod,
                    onPaymentSelect = {
                        viewModel.onPaymentSelect(checkoutUIState.paymentList[i])
                    }
                )
            }
        }
    }
}

@Composable
fun PaymentCard(paymentMethod: PaymentMethod,isPaymentSelected:Boolean,onPaymentSelect:()->Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
            .clickable {
                onPaymentSelect()
            }
    ) {
        Row(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = paymentMethod.iconData),
                contentDescription = paymentMethod.payment.name,
            )
            Text(
                text = paymentMethod.payment.name,
                modifier = Modifier.padding(horizontal = 5.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(text = "", modifier = Modifier.weight(1F))
            if (isPaymentSelected)
                Icon(
                    painter = painterResource(id = R.drawable.baseline_check_24),
                    contentDescription = null
                )
        }
    }
}
