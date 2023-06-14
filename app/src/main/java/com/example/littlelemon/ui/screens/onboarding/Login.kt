package com.example.littlelemon.ui.screens.onboarding

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemoncoursera.R
import com.example.littlelemon.helper.emailValidation
import com.example.littlelemon.helper.emptyValidation
import com.example.littlelemon.model.LittleLemonUser
import com.example.littlelemon.ui.screens.components.ActionButton
import com.example.littlelemon.ui.screens.components.TextButton
import com.example.littlelemon.ui.screens.components.TextInputField
import com.example.littlelemon.viewmodels.onboarding.OnboardingViewModel

@Composable
fun LoginPage(
    viewModel: OnboardingViewModel,
    onNavigateToHome: (LittleLemonUser) -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var emailText by remember {
        mutableStateOf("")
    }
    var emailError by remember {
        mutableStateOf("")
    }
    var passwordText by remember {
        mutableStateOf("")
    }
    var passwordError by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    AuthTemplate {
        TextInputField(label = stringResource(R.string.email_address), value = emailText, onValueChange = {
            emailText = it
        }, isError = emailError.isNotEmpty())
        if (emailError.isNotEmpty())
            Text(
                text = "* $emailError",
                modifier = Modifier.padding(horizontal = 15.dp),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Red,
                    fontSize = 10.sp
                ),
                textAlign = TextAlign.Left
            )
        TextInputField(
            label = stringResource(R.string.password),
            value = passwordText,
            onValueChange = {
                passwordText = it
            },
            isPassword = true,
            passwordVisible = uiState.obscured,
            onPasswordVisibilityChanged = { viewModel.changePasswordVisibility() },
            isError = passwordError.isNotEmpty()
        )
        if (passwordError.isNotEmpty())
            Text(
                text = "* $passwordError",
                modifier = Modifier.padding(horizontal = 15.dp),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Red,
                    fontSize = 10.sp
                )
            )
        ActionButton(
            onClick = {
                emailError =
                    if (emailText.emptyValidation()) "Email must be filled" else if (emailText.emailValidation(

                        )
                    ) "Invalid Email" else ""
                passwordError =
                    if (passwordText.emptyValidation()) "Password must be filled" else ""
                if (emailError.isEmpty() && passwordError.isEmpty()) {
                    Toast.makeText(
                        context,
                        "LoggedIn successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    onNavigateToHome(
                        LittleLemonUser(
                            firstName = "Martin",
                            lastName = "Ødegaard",
                            email = emailText,
                            password = passwordText
                        )
                    )
                }
            },
            label = stringResource(R.string.login)
        )
        TextButton(
            onClick = {
                onNavigateToRegister()
            },
            label = stringResource(R.string.don_t_have_an_account_register),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        )
    }
}