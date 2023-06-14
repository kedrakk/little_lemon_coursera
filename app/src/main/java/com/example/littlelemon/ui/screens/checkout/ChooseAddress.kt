package com.example.littlelemon.ui.screens.checkout

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.littlelemoncoursera.R
import com.example.littlelemon.data.local.entity.AddressInformation
import com.example.littlelemon.helper.emptyValidation
import com.example.littlelemon.model.AddressType
import com.example.littlelemon.navigation.Routes
import com.example.littlelemon.ui.screens.components.ActionButton
import com.example.littlelemon.ui.screens.components.CommonAppBar
import com.example.littlelemon.ui.screens.components.LocalImageLoader
import com.example.littlelemon.ui.screens.components.TextButton
import com.example.littlelemon.ui.screens.components.TextInputField
import com.example.littlelemon.viewmodels.checkout.CheckoutViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseAddressInformation(
    viewModel: CheckoutViewModel,
    navController: NavController,
    isFromCheckoutFlow: Boolean = true
) {
    val allAddressInformation = viewModel.getAllAddresses().observeAsState(initial = emptyList())
    val checkoutUIState = viewModel.uiState.collectAsState().value
    val coroutineScope = rememberCoroutineScope()
    var addressId: Int? by remember {
        mutableStateOf(null)
    }
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
    var isShowAddressForm by remember {
        mutableStateOf(false)
    }
    var receiverNameError by remember {
        mutableStateOf("")
    }
    var phoneNumberError by remember {
        mutableStateOf("")
    }
    var fullAddressError by remember {
        mutableStateOf("")
    }
    var isUpdateAddress by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CommonAppBar(
                title = if(isFromCheckoutFlow) stringResource(R.string.confirm_your_address) else stringResource(
                                    R.string.view_addresses_information),
                onBackClicked = {
                    if (navController.previousBackStackEntry != null) {
                        navController.navigateUp()
                    }
                },
            )
        },
        floatingActionButton = {
            if (!isShowAddressForm)
                LocalImageLoader(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.primary.copy(alpha = .2F))
                        .clickable {
                            addressId = null
                            receiverName = ""
                            phoneNumber = ""
                            fullAddress = ""
                            selectedAddressType = AddressType.HOME
                            isShowAddressForm = true
                        },
                    painterRes = R.drawable.baseline_add_location_alt_24,
                    imageModifier = Modifier
                        .padding(10.dp)
                        .size(25.dp)
                )
        },
        bottomBar = {
            if (isFromCheckoutFlow && !isShowAddressForm && checkoutUIState.selectedAddress != null)
                ActionButton(
                    onClick = {
                        navController.navigate(Routes.SELECT_PAYMENT.name)
                    },
                    label = stringResource(R.string.continue_label)
                )
        }
    ) { padding ->
        if (isShowAddressForm) {
            Box {
                AddAddressForm(
                    receiverName = receiverName,
                    receiverNameError = receiverNameError,
                    onReceiverNameChanged = {
                        receiverName = it
                    },
                    phoneNumber = phoneNumber,
                    phoneNumberError = phoneNumberError,
                    onPhoneNumberChanged = {
                        phoneNumber = it
                    },
                    fullAddress = fullAddress,
                    fullAddressError = fullAddressError,
                    onFullAddressChanged = {
                        fullAddress = it
                    },
                    onSaveAddress = {
                        receiverNameError =
                            if (receiverName.emptyValidation()) "Receiver Name must be filled" else ""
                        phoneNumberError =
                            if (phoneNumber.emptyValidation()) "Phone Number must be filled" else ""
                        fullAddressError =
                            if (fullAddress.emptyValidation()) "Address must be filled" else ""
                        if (receiverNameError.isEmpty() && phoneNumberError.isEmpty() && fullAddressError.isEmpty()) {
                            coroutineScope.launch {
                                if (isUpdateAddress) {
                                    viewModel.updateAddress(
                                        AddressInformation(
                                            addressId = addressId,
                                            phoneNumber = phoneNumber,
                                            receiverName = receiverName,
                                            addressDetailInformation = fullAddress,
                                            addressType = selectedAddressType
                                        )
                                    )
                                } else {
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
                            }
                            isShowAddressForm = false
                            Toast.makeText(
                                context,
                                if (isUpdateAddress) "Update address successfully" else "Save a new address successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 20.dp),
                    onCancelSave = {
                        isShowAddressForm = false
                    },
                    selectedAddressType = selectedAddressType,
                    onSelectedAddressChange = {
                        selectedAddressType = it
                    },
                    isUpdateAddress = isUpdateAddress,
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
                    selectedAddressInformation = checkoutUIState.selectedAddress,
                    onAddressSelect = {
                        viewModel.onAddressSelect(it)
                    },
                    onUpdateIconClicked = {
                        addressId = it.addressId
                        receiverName = it.receiverName
                        phoneNumber = it.phoneNumber
                        fullAddress = it.addressDetailInformation
                        selectedAddressType = it.addressType
                        isUpdateAddress = true
                        isShowAddressForm = true
                    },
                    isFromCheckoutFlow = isFromCheckoutFlow
                )
            }
        }
    }
}

@Composable
fun AddAddressForm(
    receiverName: String,
    receiverNameError: String,
    onReceiverNameChanged: (String) -> Unit,
    phoneNumber: String,
    phoneNumberError: String,
    onPhoneNumberChanged: (String) -> Unit,
    fullAddress: String,
    fullAddressError: String,
    onFullAddressChanged: (String) -> Unit,
    onSaveAddress: () -> Unit,
    onCancelSave: () -> Unit,
    modifier: Modifier,
    selectedAddressType: AddressType,
    onSelectedAddressChange: (AddressType) -> Unit,
    isUpdateAddress: Boolean,
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState(), reverseScrolling = true),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isUpdateAddress) stringResource(R.string.update_your_address) else stringResource(
                            R.string.add_your_address),
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
            isError = receiverNameError.isNotEmpty(),
            label = stringResource(R.string.enter_receiver_name),
        )
        if (receiverNameError.isNotEmpty()) {
            Text(
                text = "* $receiverNameError",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Red,
                    fontSize = 10.sp
                ),
                textAlign = TextAlign.Start,
            )
        }
        TextInputField(
            value = phoneNumber,
            onValueChange = { onPhoneNumberChanged(it) },
            isError = phoneNumberError.isNotEmpty(),
            label = stringResource(R.string.enter_phone_number),
        )
        if (phoneNumberError.isNotEmpty()) {
            Text(
                text = "* $phoneNumberError",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Red,
                    fontSize = 10.sp
                ),
                textAlign = TextAlign.Start,
            )
        }
        TextInputField(
            value = fullAddress,
            onValueChange = { onFullAddressChanged(it) },
            isError = fullAddressError.isNotEmpty(),
            label = stringResource(R.string.enter_address),
            isSingleLine = false,
            maxLines = 3,
        )
        if (fullAddressError.isNotEmpty()) {
            Text(
                text = "* $fullAddressError",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Red,
                    fontSize = 10.sp
                ),
                textAlign = TextAlign.Start,
            )
        }
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
        ActionButton(
            onClick = { onSaveAddress() },
            label = stringResource(R.string.save_address_information)
        )
        TextButton(onClick = { onCancelSave() }, label = stringResource(R.string.cancel))
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
    onAddressSelect: (AddressInformation) -> Unit,
    onUpdateIconClicked: (AddressInformation) -> Unit,
    isFromCheckoutFlow: Boolean,
) {
    LazyColumn() {
        if(isFromCheckoutFlow)
        {
            item {
                Text(
                    text = stringResource(id = R.string.select_your_address),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp, top = 10.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        items(addressList.size) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(isFromCheckoutFlow)
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
                AddressItemCard(
                    addressInformation = addressList[it],
                    onUpdateIconClicked = {
                        onUpdateIconClicked(addressList[it])
                    }
                )
            }
        }
    }
}

@Composable
fun AddressItemCard(
    addressInformation: AddressInformation,
    onUpdateIconClicked: () -> Unit,
) {
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
                onClick = {
                    onUpdateIconClicked()
                }
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
