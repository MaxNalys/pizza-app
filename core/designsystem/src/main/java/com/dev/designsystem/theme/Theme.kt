package com.dev.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val lightScheme = lightColorScheme(
    primary = colorAccent,
    onPrimary = Color.White,

    primaryContainer = colorAccent,
    onPrimaryContainer = Color.White,

    secondary = colorDarkBg,
    onSecondary = colorTxtPrimary,

    secondaryContainer = colorDarkBg,
    onSecondaryContainer = colorTxtPrimary,

    tertiary = colorAccent,
    onTertiary = Color.White,

    error = Color(0xFFB00020),
    onError = Color.White,

    background = colorBasicBg,
    onBackground = colorTxtPrimary,

    surface = colorDarkBg,
    onSurface = colorTxtPrimary,

    surfaceVariant = colorBasicBg,
    onSurfaceVariant = colorTxtSecondary,

    outline = colorTxtSecondary,

    inverseSurface = colorTxtPrimary,
    inverseOnSurface = colorBasicBg,
    inversePrimary = colorAccent
)

@Composable
fun PizzaAppTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = lightScheme


    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}