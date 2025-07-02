/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.history

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import raf.console.chitalka.ui.history.HistoryEvent

@Composable
fun HistoryBackHandler(
    showSearch: Boolean,
    searchVisibility: (HistoryEvent.OnSearchVisibility) -> Unit,
    navigateToLibrary: () -> Unit
) {
    BackHandler {
        if (showSearch) {
            searchVisibility(HistoryEvent.OnSearchVisibility(false))
            return@BackHandler
        }

        navigateToLibrary()
    }
}