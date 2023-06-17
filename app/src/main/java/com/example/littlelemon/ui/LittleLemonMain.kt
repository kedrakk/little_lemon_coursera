package com.example.littlelemon.ui

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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.littlelemon.cartItems
import com.example.littlelemon.data.local.entity.LocalDishItem
import com.example.littlelemon.model.LittleLemonUser
import com.example.littlelemon.model.MyAppTheme
import com.example.littlelemon.navigation.RouteKeys
import com.example.littlelemon.navigation.Routes
import com.example.littlelemon.ui.dialogs.LoadingDialog
import com.example.littlelemon.ui.screens.category.SearchItemPage
import com.example.littlelemon.ui.screens.checkout.CheckOutReviewPage
import com.example.littlelemon.ui.screens.checkout.ChooseAddressInformation
import com.example.littlelemon.ui.screens.checkout.SelectPaymentPage
import com.example.littlelemon.ui.screens.dish.DishDetailPage
import com.example.littlelemon.ui.screens.home.HomePage
import com.example.littlelemon.ui.screens.onboarding.LoginPage
import com.example.littlelemon.ui.screens.onboarding.RegisterPage
import com.example.littlelemon.ui.screens.onboarding.SplashPage
import com.example.littlelemon.ui.screens.profile.EditProfilePage
import com.example.littlelemon.ui.screens.profile.ViewAddressPage
import com.example.littlelemon.ui.theme.LittleLemonCourseraTheme
import com.example.littlelemon.viewmodels.cart.CartViewModel
import com.example.littlelemon.viewmodels.category.SearchViewModel
import com.example.littlelemon.viewmodels.checkout.CheckoutViewModel
import com.example.littlelemon.viewmodels.home.HomeViewModel
import com.example.littlelemon.viewmodels.main.LittleLemonMainUIState
import com.example.littlelemon.viewmodels.main.LittleLemonMainViewModel
import com.example.littlelemon.viewmodels.onboarding.OnboardingViewModel
import com.example.littlelemoncoursera.R
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

    if(uiState.langCode.isNotEmpty()){
        littleLemonMainViewModel.updateLocale(context = context, language = uiState.langCode)
    }

    LittleLemonCourseraTheme(
        darkTheme = uiState.isDarkMode
    ) {
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
                                        popUpTo(Routes.HOME.name) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        }, onNavigateToLogin = {
                            navController.navigate(Routes.LOGIN.name) {
                                popUpTo(Routes.LOGIN.name) {
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
                                        popUpTo(Routes.HOME.name) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        },
                        onNavigateToRegister = {
                            navController.navigate(Routes.REGISTER.name) {
                                popUpTo(Routes.REGISTER.name) {
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
                        },
                        cartViewModel = CartViewModel(empText = stringResource(id = R.string.cart_is_empty)),
                        onCheckoutFromCart = {dishList,total->
                            checkoutViewModel.setItemAndPrice(
                                newItemsList = dishList,
                                newPrice = total
                            )
                            navController.navigate(Routes.ADDRESS_CONFIRM.name)
                        },
                        isDarkTheme = uiState.isDarkMode,
                        onThemeChange = {isNewThemeDark->
                            CoroutineScope(Dispatchers.Default).launch {
                                // Simulate loading data
                                openDialog.value = true
                                littleLemonMainViewModel.changeTheme(
                                    MyAppTheme(isDarkMode = isNewThemeDark)
                                )
                                delay(3000)
                                openDialog.value = false

                                // Update data on the main thread
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Theme Change Success", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        onLanguageChanged = {newLang->
                            CoroutineScope(Dispatchers.Default).launch {
                                // Simulate loading data
                                openDialog.value = true
                                littleLemonMainViewModel.changeLanguage(context,newLang)
                                delay(3000)
                                openDialog.value = false

                                // Update data on the main thread
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Language Change Success", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        currentLangCode = uiState.langCode
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
                            },
                            onAddToCart = {cartItem->
                                val isAlreadyInCart = cartItems.find {data-> data.localDishItem.id == cartItem.localDishItem.id }
                                if(isAlreadyInCart==null){
                                    cartItems.add(cartItem)
                                    Toast.makeText(context,"Added to Cart",Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(context,"Already in Cart",Toast.LENGTH_SHORT).show()
                                }
                                navController.navigateUp()
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
                    CheckOutReviewPage(
                        navController = navController,
                        viewModel = checkoutViewModel,
                        onCheckoutSuccess = {
                            Toast.makeText(context, "Order Success", Toast.LENGTH_SHORT).show()
                            navController.navigate(Routes.HOME.name) {
                                popUpTo(Routes.HOME.name) {
                                    inclusive = true
                                }
                            }
                        }
                    )
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


}