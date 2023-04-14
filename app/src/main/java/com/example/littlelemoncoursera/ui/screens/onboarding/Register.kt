package com.example.littlelemoncoursera.ui.screens.onboarding

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.littlelemoncoursera.ui.screens.components.ActionButton
import com.example.littlelemoncoursera.ui.screens.components.LogoImage
import com.example.littlelemoncoursera.ui.screens.components.TextButton
import com.example.littlelemoncoursera.ui.screens.components.TextInputField
import com.example.littlelemoncoursera.viewmodels.onboarding.OnboardingViewModel

@Composable
fun RegisterPage(viewModel: OnboardingViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var firstNameText by remember {
        mutableStateOf("")
    }
    var lastNameText by remember {
        mutableStateOf("")
    }
    var emailText by remember {
        mutableStateOf("")
    }
    var passwordText by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            LogoImage(size = 90.dp)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(color = MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Let's get to know you",
                style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onPrimary)
            )
        }
        Text(
            text = "Personal Information",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
        )
        when (uiState.currentStep) {
            1 -> Column {
                TextInputField(label = "First name", value = firstNameText, onValueChange = {
                    firstNameText = it
                })
                TextInputField(label = "Last name", value = lastNameText, onValueChange = {
                    lastNameText = it
                })
                ActionButton(onClick = { viewModel.changeIndex(2) }, label = "Continue")
            }

            2 -> Column(
                Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextInputField(label = "Email Address", value = emailText, onValueChange = {
                    emailText = it
                })
                ActionButton(onClick = { viewModel.changeIndex(3) }, label = "Continue")
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
                )
                ActionButton(onClick = {
                    Toast.makeText(
                        context,
                        "$emailText is created successfully",
                        Toast.LENGTH_SHORT
                    ).show();
                }, label = "Create Account")
                TextButton(onClick = { viewModel.changeIndex(2) }, label = "Back To Previous")
            }
        }

    }
}


