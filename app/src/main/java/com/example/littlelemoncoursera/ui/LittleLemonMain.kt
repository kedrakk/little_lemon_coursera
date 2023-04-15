package com.example.littlelemoncoursera.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littlelemoncoursera.navigation.Routes
import com.example.littlelemoncoursera.ui.screens.home.HomePage
import com.example.littlelemoncoursera.ui.screens.onboarding.LoginPage
import com.example.littlelemoncoursera.ui.screens.onboarding.RegisterPage
import com.example.littlelemoncoursera.viewmodels.onboarding.OnboardingViewModel

@Composable
fun LittleLemonMainPage() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Routes.LOGIN.name) {
            composable(Routes.REGISTER.name) {
                RegisterPage(
                    viewModel = OnboardingViewModel(),
                    onNavigateToHome = {
                        navController.navigate(Routes.HOME.name){
                            popUpTo(Routes.REGISTER.name){
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Routes.LOGIN.name) {
                LoginPage(
                    viewModel = OnboardingViewModel(),
                    onNavigateToHome = {
                        navController.navigate(Routes.HOME.name){
                            popUpTo(Routes.LOGIN.name){
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Routes.HOME.name) {
                HomePage()
            }
        }
    }


}