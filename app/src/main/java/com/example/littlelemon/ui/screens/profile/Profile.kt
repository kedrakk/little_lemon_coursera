package com.example.littlelemon.ui.screens.profile

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemoncoursera.R
import com.example.littlelemon.data.local.AppLanguageList
import com.example.littlelemon.model.AppLanguage
import com.example.littlelemon.model.LittleLemonUser
import com.example.littlelemon.navigation.Routes
import com.example.littlelemon.ui.screens.components.CommonAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    littleLemonUser: LittleLemonUser,
    onLogout: () -> Unit,
    navController: NavController,
    onThemeChange: (Boolean) -> Unit,
    isDarkTheme: Boolean,
    onLanguageChanged: (String) -> Unit,
    currentLangCode: String
) {
    var isShowDropdown by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val selectedLang: AppLanguage? =
        AppLanguageList.allLanguages.firstOrNull { it.langCode == currentLangCode }
    Scaffold(
        topBar = {
            CommonAppBar(
                title = stringResource(R.string.user_profile),
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
                label = stringResource(R.string.edit_profile),
                onTap = {
                    navController.navigate(Routes.EDIT_PROFILE.name)
                },
                onTrailingTap = {
                    navController.navigate(Routes.EDIT_PROFILE.name)
                }
            )
            SettingItem(
                iconData = R.drawable.baseline_list_24,
                label = stringResource(R.string.view_orders_list),
                onTap = {
                        Toast.makeText(context,"Coming Soon",Toast.LENGTH_SHORT).show()
                },
                onTrailingTap = {}
            )
            SettingItem(
                iconData = R.drawable.baseline_edit_location_24,
                label = stringResource(R.string.view_address_information),
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
                label = stringResource(R.string.night_mode),
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
                label = stringResource(R.string.app_language),
                onTap = {
                    isShowDropdown = !isShowDropdown
                },
                onTrailingTap = {
                    isShowDropdown = !isShowDropdown
                },
                trailing = {
                    Column() {
                        Image(
                            painter = painterResource(id = selectedLang?.res ?: R.drawable.us),
                            contentDescription = "App Language",
                            modifier = Modifier
                                .size(25.dp)
                                .clickable { isShowDropdown = !isShowDropdown }
                        )
                        LanguagePopup(
                            expanded = isShowDropdown,
                            closeDropDown = {
                                isShowDropdown = false
                            },
                            items = AppLanguageList.allLanguages,
                            onClickDropDown = { lang ->
                                isShowDropdown = false
                                if (lang.langCode != currentLangCode) {
                                    onLanguageChanged(lang.langCode)
                                }
                            },
                            selectedLang = currentLangCode
                        )
                    }
                }
            )
            SettingItem(
                iconData = R.drawable.baseline_rate_review_24,
                label = stringResource(R.string.rate_app),
                onTap = {
                    Toast.makeText(context,"Coming Soon",Toast.LENGTH_SHORT).show()
                },
                onTrailingTap = {
                    Toast.makeText(context,"Coming Soon",Toast.LENGTH_SHORT).show()
                }
            )
            Divider(
                modifier = Modifier.padding(vertical = 10.dp)
            )
            SettingItem(
                iconData = R.drawable.baseline_login_24,
                label = stringResource(R.string.logout),
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
    onClickDropDown: (AppLanguage) -> Unit,
    selectedLang: String
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
                text = {
                    Text(
                        text = itemValue.name,
                        fontWeight = if(selectedLang==itemValue.langCode) FontWeight.Bold else FontWeight.Light
                    )
                },
                onClick = {
                    onClickDropDown(itemValue)
                },
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
