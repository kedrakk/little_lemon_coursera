package com.example.littlelemoncoursera.ui

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.littlelemoncoursera.data.local.entity.LocalDishItem
import com.example.littlelemoncoursera.model.LittleLemonUser
import com.example.littlelemoncoursera.navigation.RouteKeys
import com.example.littlelemoncoursera.navigation.Routes
import com.example.littlelemoncoursera.ui.dialogs.LoadingDialog
import com.example.littlelemoncoursera.ui.screens.category.SearchItemPage
import com.example.littlelemoncoursera.ui.screens.checkout.CheckOutReviewPage
import com.example.littlelemoncoursera.ui.screens.checkout.ChooseAddressInformation
import com.example.littlelemoncoursera.ui.screens.checkout.SelectPaymentPage
import com.example.littlelemoncoursera.ui.screens.dish.DishDetailPage
import com.example.littlelemoncoursera.ui.screens.home.HomePage
import com.example.littlelemoncoursera.ui.screens.onboarding.LoginPage
import com.example.littlelemoncoursera.ui.screens.onboarding.RegisterPage
import com.example.littlelemoncoursera.ui.screens.onboarding.SplashPage
import com.example.littlelemoncoursera.ui.screens.profile.EditProfilePage
import com.example.littlelemoncoursera.ui.screens.profile.ViewAddressPage
import com.example.littlelemoncoursera.viewmodels.category.SearchViewModel
import com.example.littlelemoncoursera.viewmodels.checkout.CheckoutViewModel
import com.example.littlelemoncoursera.viewmodels.home.HomeViewModel
import com.example.littlelemoncoursera.viewmodels.main.LittleLemonMainUIState
import com.example.littlelemoncoursera.viewmodels.main.LittleLemonMainViewModel
import com.example.littlelemoncoursera.viewmodels.onboarding.OnboardingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LittleLemonMainPage(
    littleLemonMainViewModel: LittleLemonMainViewModel = viewModel(
        factory = LittleLemonMainViewModel.Factory
    ),
) {
    val uiState: LittleLemonMainUIState = littleLemonMainViewModel.uiState.collectAsState().value
    val newDestination = if (uiState.littleLemonUser.email.isNotEmpty()) {
        Routes.HOME.name
    } else {
        if (uiState.isLoading) {
            Routes.SPLASH.name
        } else {
            Routes.LOGIN.name
        }
    }
    val context = LocalContext.current
    val checkoutViewModel = CheckoutViewModel()
    var dishItemList: List<LocalDishItem> = listOf()

    val openDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
    if (openDialog.value) {
        LoadingDialog {
            openDialog.value = false
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = newDestination) {
            composable(Routes.REGISTER.name) {
                RegisterPage(
                    viewModel = OnboardingViewModel(),
                    onNavigateToHome = {
                        CoroutineScope(Dispatchers.Default).launch {
                            // Simulate loading data
                            openDialog.value = true
                            littleLemonMainViewModel.setUserData(it)
                            delay(2000)
                            openDialog.value = false

                            // Update data on the main thread
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT)
                                    .show()
                                navController.navigate(Routes.HOME.name) {
                                    popUpTo(Routes.REGISTER.name) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }, onNavigateToLogin = {
                        navController.navigate(Routes.LOGIN.name) {
                            popUpTo(Routes.REGISTER.name) {
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
                        CoroutineScope(Dispatchers.Default).launch {
                            // Simulate loading data
                            openDialog.value = true
                            littleLemonMainViewModel.setUserData(it)
                            delay(2000)
                            openDialog.value = false

                            // Update data on the main thread
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT)
                                    .show()
                                navController.navigate(Routes.HOME.name) {
                                    popUpTo(Routes.LOGIN.name) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(Routes.REGISTER.name) {
                            popUpTo(Routes.LOGIN.name) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(Routes.HOME.name) {
                HomePage(
                    HomeViewModel(),
                    uiState.littleLemonUser,
                    onLogout = {


                        CoroutineScope(Dispatchers.Default).launch {
                            // Simulate loading data
                            openDialog.value = true
                            littleLemonMainViewModel.setUserData(LittleLemonUser("", "", "", ""))
                            delay(500)
                            openDialog.value = false

                            // Update data on the main thread
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Logout Success", Toast.LENGTH_SHORT).show()
                                navController.navigate(Routes.LOGIN.name) {
                                    popUpTo(Routes.LOGIN.name) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    },
                    navController = navController,
                    onSearchClicked = {
                        dishItemList = it
                        navController.navigate(Routes.SEARCH.name)
                    }
                )
            }
            composable(Routes.SPLASH.name) {
                SplashPage()
            }
            composable(Routes.SEARCH.name) {
                SearchItemPage(
                    navController = navController,
                    searchViewModel = SearchViewModel(
                        allDishes = dishItemList
                    ),
                )
            }
            composable(
                "${Routes.DISH_DETAIL.name}/{${RouteKeys.dishId}}",
                arguments = listOf(
                    navArgument(RouteKeys.dishId) {
                        type = NavType.IntType
                    }
                )
            ) {
                it.arguments?.getInt(RouteKeys.dishId)?.let { it1 ->
                    DishDetailPage(
                        dishId = it1,
                        navController = navController,
                        checkoutViewModel = checkoutViewModel,
                        onOrderNow = { dishList, total ->
                            checkoutViewModel.setItemAndPrice(
                                newItemsList = dishList,
                                newPrice = total
                            )
                        }
                    )
                }
            }
            composable(Routes.ADDRESS_CONFIRM.name) {
                ChooseAddressInformation(
                    viewModel = checkoutViewModel,
                    navController = navController
                )
            }
            composable(Routes.SELECT_PAYMENT.name) {
                SelectPaymentPage(navController = navController, viewModel = checkoutViewModel)
            }
            composable(Routes.CHECKOUT_REVIEW.name) {
                CheckOutReviewPage(navController = navController, viewModel = checkoutViewModel)
            }
            composable(Routes.EDIT_PROFILE.name) {
                EditProfilePage(
                    navController = navController,
                    littleLemonUser = uiState.littleLemonUser,
                    onEditUserInfo = { newUserInfo ->
                        CoroutineScope(Dispatchers.Default).launch {
                            // Simulate loading data
                            openDialog.value = true
                            littleLemonMainViewModel.setUserData(newUserInfo)
                            delay(2000)
                            openDialog.value = false

                            // Update data on the main thread
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Edit Profile Success", Toast.LENGTH_SHORT)
                                    .show()
                                navController.navigateUp()
                            }
                        }
                    }
                )
            }
            composable(Routes.VIEW_ADDRESSES.name) {
                ViewAddressPage(
                    viewModel = checkoutViewModel,
                    navController = navController
                )
            }
        }
    }


}