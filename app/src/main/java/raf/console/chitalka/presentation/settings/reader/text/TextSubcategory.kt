/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("FunctionName")

package raf.console.chitalka.presentation.settings.reader.text

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.settings.components.SettingsSubcategory
import raf.console.chitalka.presentation.settings.reader.text.components.LineHeightOption
import raf.console.chitalka.presentation.settings.reader.text.components.ParagraphHeightOption
import raf.console.chitalka.presentation.settings.reader.text.components.ParagraphIndentationOption
import raf.console.chitalka.presentation.settings.reader.text.components.TextAlignmentOption

fun LazyListScope.TextSubcategory(
    titleColor: @Composable () -> Color = { MaterialTheme.colorScheme.primary },
    title: @Composable () -> String = { stringResource(id = R.string.text_reader_settings) },
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
            TextAlignmentOption()
        }

        item {
            LineHeightOption()
        }

        item {
            ParagraphHeightOption()
        }

        item {
            ParagraphIndentationOption()
        }
    }
}