/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("FunctionName")

package raf.console.chitalka.presentation.settings.appearance.theme_preferences

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.settings.appearance.theme_preferences.components.AbsoluteDarkOption
import raf.console.chitalka.presentation.settings.appearance.theme_preferences.components.AppThemeOption
import raf.console.chitalka.presentation.settings.appearance.theme_preferences.components.DarkThemeOption
import raf.console.chitalka.presentation.settings.appearance.theme_preferences.components.PureDarkOption
import raf.console.chitalka.presentation.settings.appearance.theme_preferences.components.ThemeContrastOption
import raf.console.chitalka.presentation.settings.components.SettingsSubcategory

fun LazyListScope.ThemePreferencesSubcategory(
    titleColor: @Composable () -> Color = { MaterialTheme.colorScheme.primary },
    title: @Composable () -> String = { stringResource(id = R.string.theme_appearance_settings) },
    showTitle: Boolean = true,
    showDivider: Boolean = true
) {
    SettingsSubcategory(
        titleColor = titleColor,
        title = title,
        showTitle = showTitle,
        showDivider = showDivider
    ) {
        item {
            DarkThemeOption()
        }

        item {
            AppThemeOption()
        }

        item {
            ThemeContrastOption()
        }

        item {
            PureDarkOption()
        }

        item {
            AbsoluteDarkOption()
        }
    }
}