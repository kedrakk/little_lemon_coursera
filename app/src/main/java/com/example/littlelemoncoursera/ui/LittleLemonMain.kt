package com.example.littlelemoncoursera.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littlelemoncoursera.navigation.Routes
import com.example.littlelemoncoursera.ui.screens.home.HomePage
import com.example.littlelemoncoursera.ui.screens.onboarding.LoginPage
import com.example.littlelemoncoursera.ui.screens.onboarding.RegisterPage
import com.example.littlelemoncoursera.viewmodels.main.LittleLemonMainUIState
import com.example.littlelemoncoursera.viewmodels.main.LittleLemonMainViewModel
import com.example.littlelemoncoursera.viewmodels.onboarding.OnboardingViewModel

@Composable
fun LittleLemonMainPage(
    littleLemonMainViewModel: LittleLemonMainViewModel = viewModel(
        factory = LittleLemonMainViewModel.Factory
    )
) {
    val uiState:LittleLemonMainUIState = littleLemonMainViewModel.uiState.collectAsState().value
    var startDestination: String = Routes.LOGIN.name

    if(uiState.littleLemonUser.email.isNotEmpty()){
        startDestination = Routes.HOME.name
    }else{
        Log.w("Little_Lemon_Error",startDestination)
        Log.e("Little_Lemon_Error ","User has not logged in")
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = startDestination) {
            composable(Routes.REGISTER.name) {
                RegisterPage(
                    viewModel = OnboardingViewModel(),
                    onNavigateToHome = {
                        littleLemonMainViewModel.setUserData(it)
                        navController.navigate(Routes.HOME.name){
                            popUpTo(Routes.REGISTER.name){
                                inclusive = true
                            }
                        }
                    }
                , onNavigateToLogin = {
                        navController.navigate(Routes.LOGIN.name){
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
                        littleLemonMainViewModel.setUserData(it)
                        navController.navigate(Routes.HOME.name){
                            popUpTo(Routes.LOGIN.name){
                                inclusive = true
                            }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(Routes.REGISTER.name){
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