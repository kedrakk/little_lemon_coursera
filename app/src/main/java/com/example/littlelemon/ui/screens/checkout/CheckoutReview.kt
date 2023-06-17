package com.example.littlelemon.ui.screens.checkout

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemoncoursera.R
import com.example.littlelemon.model.AddressType
import com.example.littlelemon.navigation.Routes
import com.example.littlelemon.ui.screens.components.ActionButton
import com.example.littlelemon.ui.screens.components.CommonAppBar
import com.example.littlelemon.ui.screens.components.LocalImageLoader
import com.example.littlelemon.viewmodels.checkout.CheckoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutReviewPage(
    navController: NavController,
    viewModel: CheckoutViewModel,
    onCheckoutSuccess:()->Unit
) {
    val checkoutUIState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current
    Scaffold(
        topBar = {
            CommonAppBar(title = stringResource(R.string.review_your_order), onBackClicked = {
                if (navController.previousBackStackEntry != null) {
                    navController.navigateUp()
                }
            })
        },
        bottomBar = {
            ActionButton(
                onClick = {
                    onCheckoutSuccess()
                },
                label = stringResource(R.string.confirm_order)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 10.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LocalImageLoader(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.primary.copy(alpha = .2F)),
                painterRes = R.drawable.baseline_fastfood_24,
                imageModifier = Modifier
                    .padding(20.dp)
                    .size(50.dp)
            )
            ReviewItemsCard(
                title = "Deliver To",
                iconData = when (checkoutUIState.selectedAddress?.addressType) {
                    AddressType.HOME -> R.drawable.baseline_home_24
                    AddressType.WORK -> R.drawable.baseline_work_24
                    else -> R.drawable.baseline_add_location_alt_24
                },
                body = {
                    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                        if (checkoutUIState.selectedAddress != null) {
                            Text(
                                text = checkoutUIState.selectedAddress.receiverName,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = checkoutUIState.selectedAddress.phoneNumber,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Light
                                )
                            )
                            Text(
                                text = checkoutUIState.selectedAddress.addressDetailInformation,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                        }
                    }
                }
            )
            ReviewItemsCard(
                title = "Payment",
                iconData = if (checkoutUIState.selectedPaymentMethod != null) checkoutUIState.selectedPaymentMethod.iconData else R.drawable.baseline_list_24,
                body = {
                    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                        if (checkoutUIState.selectedPaymentMethod != null) {
                            Text(
                                text = checkoutUIState.selectedPaymentMethod.payment.name,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            )
            ReviewItemsCard(
                title = stringResource(R.string.review_your_selected_dished),
                iconData = R.drawable.baseline_fastfood_24,
                body = {
                    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                        for (i in 0 until checkoutUIState.selectedItems.size) {
                            Column(
                                modifier = Modifier.padding(vertical = 5.dp)
                            ) {
                                Text(
                                    text = checkoutUIState.selectedItems[i].title,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = "$ ${checkoutUIState.selectedItems[i].price}",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Light
                                    )
                                )
                            }
                        }
                    }
                }
            )
            TotalPriceItem(total = checkoutUIState.totalPrice)
        }
    }
}

@Composable
fun TotalPriceItem(total: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(text = stringResource(R.string.total), style = MaterialTheme.typography.bodySmall)
        Text(
            text = "$ $total",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun ReviewItemsCard(title: String, iconData: Int, body: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 10.dp)
    ) {
        Text(
            text = title,
            Modifier.padding(vertical = 5.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp)
            ) {
                Icon(painter = painterResource(id = iconData), contentDescription = title)
                body()
            }
        }
    }
}