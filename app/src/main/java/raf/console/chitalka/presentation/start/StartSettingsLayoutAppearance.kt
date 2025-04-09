/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("FunctionName")

package raf.console.chitalka.presentation.start

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.settings.appearance.theme_preferences.ThemePreferencesSubcategory

fun LazyListScope.StartSettingsLayoutAppearance() {
    ThemePreferencesSubcategory(
        title = { stringResource(id = R.string.start_theme_preferences) },
        titleColor = { MaterialTheme.colorScheme.secondary },
        showDivider = false
    )
}