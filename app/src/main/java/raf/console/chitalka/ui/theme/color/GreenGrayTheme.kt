/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.ui.theme.color

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val primaryLight = Color(0xFF52564B)
private val onPrimaryLight = Color(0xFFFFFFFF)
private val primaryContainerLight = Color(0xFFC7CBBC)
private val onPrimaryContainerLight = Color(0xFF161B12)
private val secondaryLight = Color(0xFF5B6055)
private val onSecondaryLight = Color(0xFFFFFFFF)
private val secondaryContainerLight = Color(0xFFE0E4D6)
private val onSecondaryContainerLight = Color(0xFF191D14)
private val tertiaryLight = Color(0xFF56624A)
private val onTertiaryLight = Color(0xFFFFFFFF)
private val tertiaryContainerLight = Color(0xFFDAE7C9)
private val onTertiaryContainerLight = Color(0xFF141E0C)
private val errorLight = Color(0xFFB3261E)
private val onErrorLight = Color(0xFFFFFFFF)
private val errorContainerLight = Color(0xFFF9DEDC)
private val onErrorContainerLight = Color(0xFF410E0B)
private val backgroundLight = Color(0xFFFCF9F6)
private val onBackgroundLight = Color(0xFF1B1C1A)
private val surfaceLight = Color(0xFFFCF9F6)
private val onSurfaceLight = Color(0xFF1B1C1A)
private val surfaceVariantLight = Color(0xFFE4E2DE)
private val onSurfaceVariantLight = Color(0xFF474744)
private val outlineLight = Color(0xFF777673)
private val outlineVariantLight = Color(0xFFC8C6C3)
private val scrimLight = Color(0xFF000000)
private val inverseSurfaceLight = Color(0xFF30302E)
private val inverseOnSurfaceLight = Color(0xFFF3F0ED)
private val inversePrimaryLight = Color(0xFFAEB2A4)
private val surfaceDimLight = Color(0xFFDCD9D7)
private val surfaceBrightLight = Color(0xFFFCF9F6)
private val surfaceContainerLowestLight = Color(0xFFFFFFFF)
private val surfaceContainerLowLight = Color(0xFFF6F3F0)
private val surfaceContainerLight = Color(0xFFF0EDEA)
private val surfaceContainerHighLight = Color(0xFFEBE8E5)
private val surfaceContainerHighestLight = Color(0xFFE4E2DE)


private val primaryDark = Color(0xFFAEB2A4)
private val onPrimaryDark = Color(0xFF292E24)
private val primaryContainerDark = Color(0xFF3D4237)
private val onPrimaryContainerDark = Color(0xFFC7CBBC)
private val secondaryDark = Color(0xFFC4C8BA)
private val onSecondaryDark = Color(0xFF2D3228)
private val secondaryContainerDark = Color(0xFF44483E)
private val onSecondaryContainerDark = Color(0xFFE0E4D6)
private val tertiaryDark = Color(0xFFBECBAE)
private val onTertiaryDark = Color(0xFF29341F)
private val tertiaryContainerDark = Color(0xFF3F4A34)
private val onTertiaryContainerDark = Color(0xFFDAE7C9)
private val errorDark = Color(0xFFF2B8B5)
private val onErrorDark = Color(0xFF601410)
private val errorContainerDark = Color(0xFF8C1D18)
private val onErrorContainerDark = Color(0xFFF9DEDC)
private val backgroundDark = Color(0xFF131312)
private val onBackgroundDark = Color(0xFFE4E2DE)
private val surfaceDark = Color(0xFF101010)
private val onSurfaceDark = Color(0xFFE4E2DE)
private val surfaceVariantDark = Color(0xFF474744)
private val onSurfaceVariantDark = Color(0xFFC8C6C3)
private val outlineDark = Color(0xFF92918D)
private val outlineVariantDark = Color(0xFF474744)
private val scrimDark = Color(0xFF000000)
private val inverseSurfaceDark = Color(0xFFE4E2DE)
private val inverseOnSurfaceDark = Color(0xFF30302E)
private val inversePrimaryDark = Color(0xFF52564B)
private val surfaceDimDark = Color(0xFF131312)
private val surfaceBrightDark = Color(0xFF3A3937)
private val surfaceContainerLowestDark = Color(0xFF0A0A09)
private val surfaceContainerLowDark = Color(0xFF1B1C1A)
private val surfaceContainerDark = Color(0xFF20201E)
private val surfaceContainerHighDark = Color(0xFF2A2A28)
private val surfaceContainerHighestDark = Color(0xFF353533)


@Composable
fun greenGrayTheme(isDark: Boolean): ColorScheme {
    return if (isDark) {
        darkColorScheme(
            primary = primaryDark,
            onPrimary = onPrimaryDark,
            primaryContainer = primaryContainerDark,
            onPrimaryContainer = onPrimaryContainerDark,
            secondary = secondaryDark,
            onSecondary = onSecondaryDark,
            secondaryContainer = secondaryContainerDark,
            onSecondaryContainer = onSecondaryContainerDark,
            tertiary = tertiaryDark,
            onTertiary = onTertiaryDark,
            tertiaryContainer = tertiaryContainerDark,
            onTertiaryContainer = onTertiaryContainerDark,
            error = errorDark,
            onError = onErrorDark,
            errorContainer = errorContainerDark,
            onErrorContainer = onErrorContainerDark,
            background = backgroundDark,
            onBackground = onBackgroundDark,
            surface = surfaceDark,
            onSurface = onSurfaceDark,
            surfaceVariant = surfaceVariantDark,
            onSurfaceVariant = onSurfaceVariantDark,
            outline = outlineDark,
            outlineVariant = outlineVariantDark,
            scrim = scrimDark,
            inverseSurface = inverseSurfaceDark,
            inverseOnSurface = inverseOnSurfaceDark,
            inversePrimary = inversePrimaryDark,
            surfaceDim = surfaceDimDark,
            surfaceBright = surfaceBrightDark,
            surfaceContainerLowest = surfaceContainerLowestDark,
            surfaceContainerLow = surfaceContainerLowDark,
            surfaceContainer = surfaceContainerDark,
            surfaceContainerHigh = surfaceContainerHighDark,
            surfaceContainerHighest = surfaceContainerHighestDark,
        )
    } else {
        lightColorScheme(
            primary = primaryLight,
            onPrimary = onPrimaryLight,
            primaryContainer = primaryContainerLight,
            onPrimaryContainer = onPrimaryContainerLight,
            secondary = secondaryLight,
            onSecondary = onSecondaryLight,
            secondaryContainer = secondaryContainerLight,
            onSecondaryContainer = onSecondaryContainerLight,
            tertiary = tertiaryLight,
            onTertiary = onTertiaryLight,
            tertiaryContainer = tertiaryContainerLight,
            onTertiaryContainer = onTertiaryContainerLight,
            error = errorLight,
            onError = onErrorLight,
            errorContainer = errorContainerLight,
            onErrorContainer = onErrorContainerLight,
            background = backgroundLight,
            onBackground = onBackgroundLight,
            surface = surfaceLight,
            onSurface = onSurfaceLight,
            surfaceVariant = surfaceVariantLight,
            onSurfaceVariant = onSurfaceVariantLight,
            outline = outlineLight,
            outlineVariant = outlineVariantLight,
            scrim = scrimLight,
            inverseSurface = inverseSurfaceLight,
            inverseOnSurface = inverseOnSurfaceLight,
            inversePrimary = inversePrimaryLight,
            surfaceDim = surfaceDimLight,
            surfaceBright = surfaceBrightLight,
            surfaceContainerLowest = surfaceContainerLowestLight,
            surfaceContainerLow = surfaceContainerLowLight,
            surfaceContainer = surfaceContainerLight,
            surfaceContainerHigh = surfaceContainerHighLight,
            surfaceContainerHighest = surfaceContainerHighestLight,
        )
    }
}