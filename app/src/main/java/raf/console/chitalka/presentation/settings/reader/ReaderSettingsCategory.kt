/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("FunctionName")

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import raf.console.chitalka.presentation.settings.reader.chapters.ChaptersSubcategory
import raf.console.chitalka.presentation.settings.reader.font.FontSubcategory
import raf.console.chitalka.presentation.settings.reader.images.ImagesSubcategory
import raf.console.chitalka.presentation.settings.reader.misc.MiscSubcategory
import raf.console.chitalka.presentation.settings.reader.padding.PaddingSubcategory
import raf.console.chitalka.presentation.settings.reader.progress.ProgressSubcategory
import raf.console.chitalka.presentation.settings.reader.reading_mode.ReadingModeSubcategory
import raf.console.chitalka.presentation.settings.reader.reading_speed.ReadingSpeedSubcategory
import raf.console.chitalka.presentation.settings.reader.system.SystemSubcategory
import raf.console.chitalka.presentation.settings.reader.text.TextSubcategory
import raf.console.chitalka.presentation.settings.reader.translator.TranslatorSubcategory

fun LazyListScope.ReaderSettingsCategory(
    titleColor: @Composable () -> Color = { MaterialTheme.colorScheme.primary }
) {
    FontSubcategory(
        titleColor = titleColor
    )
    TextSubcategory(
        titleColor = titleColor
    )
    ImagesSubcategory(
        titleColor = titleColor
    )
    ChaptersSubcategory(
        titleColor = titleColor
    )
    ReadingModeSubcategory(
        titleColor = titleColor
    )
    PaddingSubcategory(
        titleColor = titleColor
    )
    SystemSubcategory(
        titleColor = titleColor
    )
    ReadingSpeedSubcategory(
        titleColor = titleColor
    )
    ProgressSubcategory(
        titleColor = titleColor
    )
    TranslatorSubcategory(
        titleColor = titleColor
    )
    MiscSubcategory(
        titleColor = titleColor,
        showDivider = false
    )
}