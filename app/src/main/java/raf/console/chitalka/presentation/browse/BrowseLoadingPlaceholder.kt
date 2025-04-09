/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.browse

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import raf.console.chitalka.presentation.core.components.progress_indicator.CircularProgressIndicator

@Composable
fun BoxScope.BrowseLoadingPlaceholder(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.align(Alignment.Center)
    )
}