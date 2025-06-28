/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.reader

import androidx.compose.runtime.Composable
import raf.console.chitalka.domain.util.BottomSheet
import raf.console.chitalka.ui.reader.ReaderEvent
import raf.console.chitalka.ui.reader.ReaderScreen

@Composable
fun ReaderBottomSheet(
    bottomSheet: BottomSheet?,
    fullscreenMode: Boolean,
    menuVisibility: (ReaderEvent.OnMenuVisibility) -> Unit,
    dismissBottomSheet: (ReaderEvent.OnDismissBottomSheet) -> Unit
) {
    when (bottomSheet) {
        ReaderScreen.SETTINGS_BOTTOM_SHEET -> {
            ReaderSettingsBottomSheet(
                fullscreenMode = fullscreenMode,
                menuVisibility = menuVisibility,
                dismissBottomSheet = dismissBottomSheet
            )
        }
    }
}