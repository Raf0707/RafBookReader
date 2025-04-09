/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.browse

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import raf.console.chitalka.domain.browse.display.BrowseLayout
import raf.console.chitalka.domain.browse.SelectableFile

@Composable
fun BrowseItem(
    modifier: Modifier = Modifier,
    layout: BrowseLayout,
    file: SelectableFile,
    hasSelectedItems: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    when (layout) {
        BrowseLayout.LIST -> {
            BrowseListItem(
                modifier = modifier,
                file = file,
                hasSelectedItems = hasSelectedItems,
                onClick = onClick,
                onLongClick = onLongClick
            )
        }

        BrowseLayout.GRID -> {
            BrowseGridItem(
                modifier = modifier,
                file = file,
                hasSelectedItems = hasSelectedItems,
                onClick = onClick,
                onLongClick = onLongClick
            )
        }
    }
}