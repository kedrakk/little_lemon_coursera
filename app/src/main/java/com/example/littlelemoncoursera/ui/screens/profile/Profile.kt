package com.example.littlelemoncoursera.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemoncoursera.R
import com.example.littlelemoncoursera.model.LittleLemonUser


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(littleLemonUser: LittleLemonUser, onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "User Profile", style = MaterialTheme.typography.bodyMedium)
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            UserInformation(littleLemonUser = littleLemonUser)
            SettingItem(iconData = R.drawable.baseline_edit_24, label = "Edit Profile", onTap = {})
            SettingItem(iconData = R.drawable.baseline_list_24, label = "View Orders List", onTap = {})
            SettingItem(iconData = R.drawable.baseline_edit_location_24, label = "View Address Information", onTap = {})
            Divider(
                modifier = Modifier.padding(vertical = 10.dp)
            )
            SettingItem(iconData = R.drawable.baseline_format_paint_24, label = "App Theme", onTap = {})
            SettingItem(iconData = R.drawable.baseline_g_translate_24, label = "App Language", onTap = {})
            SettingItem(iconData = R.drawable.baseline_rate_review_24, label = "Rate App", onTap = {})
            Divider(
                modifier = Modifier.padding(vertical = 10.dp)
            )
            SettingItem(
                iconData = R.drawable.baseline_login_24,
                label = "Logout",
                onTap = {
                    onLogout()
                }
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
       Image(
           painter = painterResource(id = R.drawable.baseline_account_circle_24),
           contentDescription = "Profile",
           modifier = Modifier.size(80.dp),
           colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
       )
        Column(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(
                text = littleLemonUser.email,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = littleLemonUser.firstName+" "+littleLemonUser.lastName,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

@Composable
fun SettingItem(iconData:Int, label:String,onTap: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {onTap()}
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
        Image(
            painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
            contentDescription = label,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
            modifier = Modifier.size(18.dp)
        )
    }
}

@Preview
@Composable
fun ProfileContentPreview() {
    ProfileContent(
        littleLemonUser = LittleLemonUser(
            firstName = "Khin",
            lastName = "EaindraKyaw",
            email = "kedk@gmail.com",
            password = "123456"
        ),
    ) {

    }
}