package org.goodexpert.apps.smartpay.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = PattensBlue,
    primaryVariant = ChateauGreen,
    secondary = DarkCerulean,
    background = PattensBlue,
    surface = PureWhite,
    error = PureRed,
    onPrimary = PureBlack,
    onSecondary = PureWhite,
    onBackground = PureBlack,
    onError = PureWhite
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = PattensBlue,
    primaryVariant = ChateauGreen,
    secondary = DarkCerulean,
    background = PattensBlue,
    surface = PureWhite,
    error = PureRed,
    onPrimary = PureBlack,
    onSecondary = PureWhite,
    onBackground = PureBlack,
    onError = PureWhite
)

@Composable
fun SmartPayTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}