package com.example.littlelemoncoursera.ui.screens.checkout

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemoncoursera.R
import com.example.littlelemoncoursera.data.local.entity.AddressInformation
import com.example.littlelemoncoursera.model.AddressType
import com.example.littlelemoncoursera.navigation.Routes
import com.example.littlelemoncoursera.ui.screens.components.ActionButton
import com.example.littlelemoncoursera.ui.screens.components.CommonAppBar
import com.example.littlelemoncoursera.ui.screens.components.LocalImageLoader
import com.example.littlelemoncoursera.ui.screens.components.TextButton
import com.example.littlelemoncoursera.ui.screens.components.TextInputField
import com.example.littlelemoncoursera.viewmodels.checkout.CheckoutViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseAddressInformation(viewModel: CheckoutViewModel, navController: NavController) {
    val allAddressInformation = viewModel.getAllAddresses().observeAsState(initial = emptyList())
    val checkoutUIState = viewModel.uiState.collectAsState().value
    val coroutineScope = rememberCoroutineScope()
    var receiverName by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var fullAddress by remember {
        mutableStateOf("")
    }
    var selectedAddressType by remember {
        mutableStateOf(AddressType.HOME)
    }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CommonAppBar(title = "Confirm Your Address") {
                if (navController.previousBackStackEntry != null) {
                    navController.navigateUp()
                }
            }
        },
        floatingActionButton = {
            if (!checkoutUIState.showAddForm)
                LocalImageLoader(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.primary.copy(alpha = .2F))
                        .clickable {
                            viewModel.showOrHideAddForm(true)
                        },
                    painterRes = R.drawable.baseline_add_location_alt_24,
                    imageModifier = Modifier
                        .padding(10.dp)
                        .size(25.dp)
                )
        },
        bottomBar = {
            if (!checkoutUIState.showAddForm && checkoutUIState.selectedAddress != null)
                ActionButton(
                    onClick = {
                        navController.navigate(Routes.SELECT_PAYMENT.name)
                    },
                    label = "Continue"
                )
        }
    ) { padding ->
        if (checkoutUIState.showAddForm) {
            Box {
                AddAddressForm(
                    receiverName = receiverName,
                    onReceiverNameChanged = {
                        receiverName = it
                    },
                    phoneNumber = phoneNumber,
                    onPhoneNumberChanged = {
                        phoneNumber = it
                    },
                    fullAddress = fullAddress,
                    onFullAddressChanged = {
                        fullAddress = it
                    },
                    onSaveAddress = {

                        coroutineScope.launch {
                            viewModel.addANewAddress(
                                AddressInformation(
                                    addressId = null,
                                    phoneNumber = phoneNumber,
                                    receiverName = receiverName,
                                    addressDetailInformation = fullAddress,
                                    addressType = selectedAddressType
                                )
                            )
                        }
                        Toast.makeText(
                            context,
                            "Save a new address successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 20.dp),
                    onCancelSave = {
                        viewModel.showOrHideAddForm(false)
                    },
                    selectedAddressType = selectedAddressType,
                    onSelectedAddressChange = {
                        selectedAddressType = it
                    }
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 20.dp)
            ) {
                ShowAddressList(
                    addressList = allAddressInformation.value,
                    selectedAddressInformation = checkoutUIState.selectedAddress
                ) {
                    viewModel.onAddressSelect(it)
                }
            }
        }
    }
}

@Composable
fun AddAddressForm(
    receiverName: String,
    onReceiverNameChanged: (String) -> Unit,
    phoneNumber: String,
    onPhoneNumberChanged: (String) -> Unit,
    fullAddress: String,
    onFullAddressChanged: (String) -> Unit,
    onSaveAddress: () -> Unit,
    onCancelSave: () -> Unit,
    modifier: Modifier,
    selectedAddressType: AddressType,
    onSelectedAddressChange: (AddressType) -> Unit,
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState(), reverseScrolling = true),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add Your Address",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, top = 10.dp),
            textAlign = TextAlign.Center
        )
        LocalImageLoader(
            modifier = Modifier
                .padding(10.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.primary.copy(alpha = .2F)),
            painterRes = R.drawable.baseline_add_location_alt_24,
            imageModifier = Modifier
                .padding(20.dp)
                .size(50.dp)
        )
        TextInputField(
            value = receiverName,
            onValueChange = {
                onReceiverNameChanged(it)
            },
            isError = false,
            label = "Enter Receiver Name",
        )
        TextInputField(
            value = phoneNumber,
            onValueChange = { onPhoneNumberChanged(it) },
            isError = false,
            label = "Enter Phone Number",
        )
        TextInputField(
            value = fullAddress,
            onValueChange = { onFullAddressChanged(it) },
            isError = false,
            label = "Enter Address",
            isSingleLine = false,
            maxLines = 3,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SelectAddressType(
                label = AddressType.HOME.name,
                isSelected = selectedAddressType == AddressType.HOME
            ) {
                onSelectedAddressChange(AddressType.HOME)
            }
            SelectAddressType(
                label = AddressType.WORK.name,
                isSelected = selectedAddressType == AddressType.WORK
            ) {
                onSelectedAddressChange(AddressType.WORK)
            }
            SelectAddressType(
                label = AddressType.OTHERS.name,
                isSelected = selectedAddressType == AddressType.OTHERS
            ) {
                onSelectedAddressChange(AddressType.OTHERS)
            }
        }
        ActionButton(onClick = { onSaveAddress() }, label = "Save Address Information")
        TextButton(onClick = { onCancelSave() }, label = "Cancel")
    }
}

@Composable
fun SelectAddressType(label: String, isSelected: Boolean, onSelect: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = isSelected, onClick = { onSelect() })
        Text(text = label, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun ShowAddressList(
    addressList: List<AddressInformation>,
    selectedAddressInformation: AddressInformation?,
    onAddressSelect: (AddressInformation) -> Unit
) {
    LazyColumn() {
        item {
            Text(
                text = "Select Your Address",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp, top = 10.dp),
                textAlign = TextAlign.Center
            )
        }
        items(addressList.size) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onAddressSelect(addressList[it]) }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (selectedAddressInformation != null && selectedAddressInformation.addressId == addressList[it].addressId) R.drawable.baseline_radio_button_checked_24 else R.drawable.baseline_radio_button_unchecked_24
                        ),
                        contentDescription = "select",
                        modifier = Modifier.padding(end = 5.dp)
                    )
                }
                AddressItemCard(addressInformation = addressList[it])
            }
        }
    }
}

@Composable
fun AddressItemCard(addressInformation: AddressInformation) {
    val iconId = when (addressInformation.addressType) {
        AddressType.HOME -> R.drawable.baseline_home_24
        AddressType.WORK -> R.drawable.baseline_work_24
        else -> R.drawable.baseline_list_24
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .weight(3F)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = iconId),
                        contentDescription = addressInformation.addressType.name,
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(20.dp),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                    Text(
                        text = addressInformation.addressType.name,
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Row(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(
                        text = addressInformation.receiverName,
                        modifier = Modifier.weight(1F),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = addressInformation.phoneNumber,
                        modifier = Modifier.weight(1F),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(
                    text = addressInformation.addressDetailInformation,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Light
                    )
                )
            }
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_edit_location_24),
                    contentDescription = "Edit Address",
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1F)
                )
            }
        }
    }
}
