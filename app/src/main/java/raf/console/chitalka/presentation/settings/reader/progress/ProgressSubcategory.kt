/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("FunctionName")

package raf.console.chitalka.presentation.settings.reader.progress

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.settings.components.SettingsSubcategory
import raf.console.chitalka.presentation.settings.reader.progress.components.ProgressBarAlignmentOption
import raf.console.chitalka.presentation.settings.reader.progress.components.ProgressBarFontSizeOption
import raf.console.chitalka.presentation.settings.reader.progress.components.ProgressBarOption
import raf.console.chitalka.presentation.settings.reader.progress.components.ProgressBarPaddingOption
import raf.console.chitalka.presentation.settings.reader.progress.components.ProgressCountOption

fun LazyListScope.ProgressSubcategory(
    titleColor: @Composable () -> Color = { MaterialTheme.colorScheme.primary },
    title: @Composable () -> String = { stringResource(id = R.string.progress_reader_settings) },
    showTitle: Boolean = true,
    showDivider: Boolean = true
) {
    SettingsSubcategory(
        titleColor = titleColor,
        title = title,
        showTitle = showTitle,
        showDivider = showDivider,
    ) {
        item {
            ProgressCountOption()
        }

        item {
            ProgressBarOption()
        }

        item {
            ProgressBarFontSizeOption()
        }

        item {
            ProgressBarPaddingOption()
        }

        item {
            ProgressBarAlignmentOption()
        }
    }
}