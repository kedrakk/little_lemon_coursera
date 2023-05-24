package com.example.littlelemoncoursera.ui.screens.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemoncoursera.helper.emailValidation
import com.example.littlelemoncoursera.helper.emptyValidation
import com.example.littlelemoncoursera.model.LittleLemonUser
import com.example.littlelemoncoursera.ui.screens.components.ActionButton
import com.example.littlelemoncoursera.ui.screens.components.CommonAppBar
import com.example.littlelemoncoursera.ui.screens.components.TextInputField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfilePage(navController: NavController, littleLemonUser: LittleLemonUser,onEditUserInfo:(LittleLemonUser)->Unit,) {
    var firstNameTextBox by remember {
        mutableStateOf(littleLemonUser.firstName)
    }
    var firstNameError by remember {
        mutableStateOf("")
    }
    var lastNameTextBox by remember {
        mutableStateOf(littleLemonUser.lastName)
    }
    var lastNameError by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            CommonAppBar(
                title = "Edit Profile",
                onBackClicked = {
                    if (navController.previousBackStackEntry != null) {
                        navController.navigateUp()
                    }
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                UserImageCircleView(userEmail = littleLemonUser.email)
            }
            TextInputField(
                label = "First Name",
                value = firstNameTextBox,
                onValueChange = { newValue ->
                    firstNameTextBox = newValue
                },
                isError = firstNameError.isNotEmpty()
            )
            if (firstNameError.isNotEmpty())
                Text(
                    text = "* $firstNameError",
                    modifier = Modifier.padding(horizontal = 15.dp),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Red,
                        fontSize = 10.sp
                    ),
                    textAlign = TextAlign.Left
                )
            TextInputField(
                label = "Last Name",
                value = lastNameTextBox,
                onValueChange = { newValue ->
                    lastNameTextBox = newValue
                },
                isError = lastNameError.isNotEmpty()
            )
            if (lastNameError.isNotEmpty())
                Text(
                    text = "* $lastNameError",
                    modifier = Modifier.padding(horizontal = 15.dp),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Red,
                        fontSize = 10.sp
                    ),
                    textAlign = TextAlign.Left
                )
            TextInputField(
                label = "Email",
                value = littleLemonUser.email,
                onValueChange = { },
                isError = false,
                isEnabled = false,
            )
            ActionButton(
                onClick = {
                    firstNameError =
                        if (firstNameTextBox.emptyValidation()) "First Name must be filled" else ""
                    lastNameError =
                        if (lastNameTextBox.emptyValidation()) "Last Name must be filled" else ""
                    if (firstNameError.isEmpty() && lastNameError.isEmpty()) {
                        onEditUserInfo(
                            LittleLemonUser(firstName = firstNameTextBox, lastName = lastNameTextBox, email = littleLemonUser.email, password = littleLemonUser.password,)
                        )
                    }
                },
                label = "Edit Now"
            )
        }
    }
}