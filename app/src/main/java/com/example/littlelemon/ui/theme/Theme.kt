package com.example.littlelemon.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColorYellow,
    secondary = SecondaryColorWhite,
    tertiary = SecondaryColorLightOrange,
    onPrimary = SecondaryColorBlack,
    errorContainer = DisabledColor
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColorBlue,
    secondary = SecondaryColorBlack,
    tertiary = SecondaryColorDarkOrange,
    onPrimary = SecondaryColorWhite,
    errorContainer = DisabledColor
)

@Composable
fun LittleLemonCourseraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}