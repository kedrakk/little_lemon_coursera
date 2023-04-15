package com.example.littlelemoncoursera.ui.screens.onboarding

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemoncoursera.model.LittleLemonUser
import com.example.littlelemoncoursera.ui.screens.components.ActionButton
import com.example.littlelemoncoursera.ui.screens.components.TextButton
import com.example.littlelemoncoursera.ui.screens.components.TextInputField
import com.example.littlelemoncoursera.viewmodels.onboarding.OnboardingViewModel

@Composable
fun RegisterPage(
    viewModel: OnboardingViewModel,
    onNavigateToHome: (LittleLemonUser) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var firstNameText by remember {
        mutableStateOf("")
    }
    var firstNameError by remember {
        mutableStateOf("")
    }
    var lastNameText by remember {
        mutableStateOf("")
    }
    var lastNameError by remember {
        mutableStateOf("")
    }
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
        Column {
            when (uiState.currentStep) {
                1 -> Column {
                    TextInputField(
                        label = "First name",
                        value = firstNameText,
                        onValueChange = {
                            firstNameText = it
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
                            )
                        )
                    TextInputField(label = "Last name", value = lastNameText, onValueChange = {
                        lastNameText = it
                    }, isError = lastNameError.isNotEmpty())
                    if (lastNameError.isNotEmpty())
                        Text(
                            text = "* $lastNameError",
                            modifier = Modifier.padding(horizontal = 15.dp),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.Red,
                                fontSize = 10.sp
                            )
                        )
                    ActionButton(
                        onClick = {
                            firstNameError =
                                if (viewModel.emptyValidation(firstNameText)) "First Name must be filled" else ""
                            lastNameError =
                                if (viewModel.emptyValidation(lastNameText)) "Last Name must be filled" else ""
                            if (firstNameError.isEmpty() && lastNameError.isEmpty()) {
                                viewModel.changeIndex(2)
                            }
                        },
                        label = "Continue"
                    )
                }

                2 -> Column(
                    Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextInputField(label = "Email Address", value = emailText, onValueChange = {
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
                    ActionButton(
                        onClick = {
                            emailError =
                                if (viewModel.emptyValidation(emailText)) "Email must be filled" else if (viewModel.emailValidation(
                                        emailText
                                    )
                                ) "Invalid Email" else ""
                            if (emailError.isEmpty()) {
                                viewModel.changeIndex(3)
                            }
                        },
                        label = "Continue"
                    )
                    TextButton(onClick = { viewModel.changeIndex(1) }, label = "Back To Previous")
                }

                else -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TextInputField(
                        label = "Password",
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
                            passwordError =
                                if (viewModel.emptyValidation(passwordText)) "Password must be filled" else ""
                            if (passwordError.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "$emailText is created successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                onNavigateToHome(
                                    LittleLemonUser(
                                        firstName = firstNameText,
                                        lastName = lastNameText,
                                        email = emailText,
                                        password = passwordText
                                    )
                                )
                            }
                        },
                        label = "Create Account"
                    )
                    TextButton(onClick = { viewModel.changeIndex(2) }, label = "Back To Previous")
                }
            }

            TextButton(
                onClick = {
                    onNavigateToLogin()
                },
                label = "Already have an account? Login",
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            )
        }

    }
}



