package com.example.littlelemoncoursera.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemoncoursera.R
import com.example.littlelemoncoursera.data.local.AppLanguageList
import com.example.littlelemoncoursera.model.AppLanguage
import com.example.littlelemoncoursera.model.LittleLemonUser
import com.example.littlelemoncoursera.navigation.Routes
import com.example.littlelemoncoursera.ui.screens.components.CommonAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    littleLemonUser: LittleLemonUser,
    onLogout: () -> Unit,
    navController: NavController,
    onThemeChange: (Boolean) -> Unit,
    isDarkTheme: Boolean,
) {
    var isShowDropdown by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            CommonAppBar(
                title = "User Profile",
                onBackClicked = { /*TODO*/ },
                isBackIconContains = false
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it),
        ) {
            UserInformation(littleLemonUser = littleLemonUser)
            SettingItem(
                iconData = R.drawable.baseline_edit_24,
                label = "Edit Profile",
                onTap = {
                    navController.navigate(Routes.EDIT_PROFILE.name)
                },
                onTrailingTap = {
                    navController.navigate(Routes.EDIT_PROFILE.name)
                }
            )
            SettingItem(
                iconData = R.drawable.baseline_list_24,
                label = "View Orders List",
                onTap = {},
                onTrailingTap = {}
            )
            SettingItem(
                iconData = R.drawable.baseline_edit_location_24,
                label = "View Address Information",
                onTap = {
                    navController.navigate(Routes.VIEW_ADDRESSES.name)
                },
                onTrailingTap = {
                    navController.navigate(Routes.VIEW_ADDRESSES.name)
                }
            )
            Divider(
                modifier = Modifier.padding(vertical = 10.dp)
            )
            SettingItem(
                iconData = R.drawable.baseline_dark_mode_24,
                label = "Night Mode",
                onTap = {},
                trailing = {
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { darkMode ->
                            onThemeChange(darkMode)
                        }
                    )
                },
                verticalPaddingValue = 3.dp,
                onTrailingTap = {

                }
            )
            SettingItem(
                iconData = R.drawable.baseline_g_translate_24,
                label = "App Language",
                onTap = {
                    isShowDropdown = !isShowDropdown
                },
                onTrailingTap = {
                    isShowDropdown = !isShowDropdown
                },
                trailing = {
                    Column() {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                            contentDescription = "App Language",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                            modifier = Modifier
                                .size(18.dp)
                                .clickable { isShowDropdown = !isShowDropdown }
                        )
                        LanguagePopup(
                            expanded = isShowDropdown,
                            closeDropDown = {
                                isShowDropdown = false
                            },
                            items = AppLanguageList.allLanguages,
                            onClickDropDown = {
                                isShowDropdown = false
                            }
                        )
                    }
                }
            )
            SettingItem(
                iconData = R.drawable.baseline_rate_review_24,
                label = "Rate App",
                onTap = {},
                onTrailingTap = {}
            )
            Divider(
                modifier = Modifier.padding(vertical = 10.dp)
            )
            SettingItem(
                iconData = R.drawable.baseline_login_24,
                label = "Logout",
                onTap = {
                    onLogout()
                },
                onTrailingTap = {
                    onLogout()
                }
            )
        }
    }
}

@Composable
fun LanguagePopup(
    expanded: Boolean,
    closeDropDown: () -> Unit,
    items: List<AppLanguage>,
    onClickDropDown: (AppLanguage) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            closeDropDown()
        },
    ) {
        // adding items
        items.forEachIndexed { _, itemValue ->
            DropdownMenuItem(
                text = { Text(text = itemValue.langName) },
                onClick = { onClickDropDown(itemValue) }
            )
        }
    }
}

@Composable
fun UserInformation(littleLemonUser: LittleLemonUser) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserImageCircleView(userEmail = littleLemonUser.email)
        Column(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(
                text = littleLemonUser.email,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = littleLemonUser.firstName + " " + littleLemonUser.lastName,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

@Composable
fun UserImageCircleView(userEmail: String = "") {
    Image(
        painter = painterResource(id = R.drawable.profile),
        contentDescription = userEmail,
        modifier = Modifier.size(80.dp)
    )
}

@Composable
fun SettingItem(
    iconData: Int,
    label: String,
    onTap: () -> Unit,
    onTrailingTap: () -> Unit,
    verticalPaddingValue: Dp = 15.dp,
    trailing: @Composable() () -> Unit = {
        Image(
            painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
            contentDescription = label,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
            modifier = Modifier
                .size(18.dp)
                .clickable { onTrailingTap() }
        )
    }
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = verticalPaddingValue)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onTap() }
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconData),
            contentDescription = label,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
        )
        Text(
            text = label,
            modifier = Modifier.padding(start = 10.dp)
        )
        Box(
            modifier = Modifier.weight(1F),
        )
        trailing()
    }
}
