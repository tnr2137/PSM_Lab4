package com.example.kalkulator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = DarkBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    outlineVariant = DarkOutline,

    surfaceContainerHigh = DarkNumberContainer,
    secondaryContainer = DarkOperatorContainer,
    onSecondaryContainer = DarkOnOperator,
    tertiaryContainer = DarkActionContainer,
    onTertiaryContainer = DarkOnAction,
    primary = DarkEqualsContainer,
    onPrimary = DarkOnEquals,
    surfaceContainerHighest = DarkOperatorContainer
)

private val LightColorScheme = lightColorScheme(
    background = LightBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    outlineVariant = LightOutline,

    surfaceContainerHigh = LightNumberContainer,
    secondaryContainer = LightOperatorContainer,
    onSecondaryContainer = LightOnOperator,
    tertiaryContainer = LightActionContainer,
    onTertiaryContainer = LightOnAction,
    primary = LightEqualsContainer,
    onPrimary = LightOnEquals,
    surfaceContainerHighest = LightOperatorContainer
)

@Composable
fun KalkulatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}