/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.reader

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.ui.reader.ReaderEvent

@Composable
fun ReaderBottomBarSlider(
    book: Book,
    lockMenu: Boolean,
    listState: LazyListState,
    scroll: (ReaderEvent.OnScroll) -> Unit,
    changeProgress: (ReaderEvent.OnChangeProgress) -> Unit
) {
    Slider(
        value = book.progress,
        enabled = !lockMenu,
        onValueChange = {
            if (listState.layoutInfo.totalItemsCount > 0) {
                scroll(ReaderEvent.OnScroll(it))
                changeProgress(
                    ReaderEvent.OnChangeProgress(
                        progress = it,
                        firstVisibleItemIndex = listState.firstVisibleItemIndex,
                        firstVisibleItemOffset = 0
                    )
                )
            }
        },
        colors = SliderDefaults.colors(
            inactiveTrackColor = MaterialTheme.colorScheme.secondary.copy(0.15f),
            disabledActiveTrackColor = MaterialTheme.colorScheme.primary,
            disabledThumbColor = MaterialTheme.colorScheme.primary,
            disabledInactiveTrackColor = MaterialTheme.colorScheme.secondary.copy(0.15f),
        )
    )
}