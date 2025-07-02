/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("FunctionName")

package raf.console.chitalka.presentation.settings.general

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import raf.console.chitalka.presentation.settings.general.components.AppLanguageOption
import raf.console.chitalka.presentation.settings.general.components.DoublePressExitOption

fun LazyListScope.GeneralSettingsCategory(
    topPadding: Dp = 16.dp,
    bottomPadding: Dp = 16.dp
) {
    item {
        Spacer(modifier = Modifier.height((topPadding - 8.dp).coerceAtLeast(0.dp)))
    }

    item {
        AppLanguageOption()
    }

    /*item {
        CheckForUpdatesOption()
    }*/

    item {
        DoublePressExitOption()
    }

    item {
        Spacer(modifier = Modifier.height(bottomPadding))
    }
}