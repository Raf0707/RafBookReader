/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.browse

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import raf.console.chitalka.presentation.core.components.common.LazyColumnWithScrollbar

@Composable
fun BrowseSettingsLayout(
    listState: LazyListState,
    paddingValues: PaddingValues
) {
    LazyColumnWithScrollbar(
        Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding()),
        state = listState
    ) {
        BrowseSettingsCategory()
    }
}