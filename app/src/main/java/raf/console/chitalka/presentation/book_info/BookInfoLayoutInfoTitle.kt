/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.book_info

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.presentation.core.components.common.StyledText
import raf.console.chitalka.presentation.core.util.noRippleClickable
import raf.console.chitalka.ui.book_info.BookInfoEvent

@Composable
fun BookInfoLayoutInfoTitle(
    book: Book,
    showTitleDialog: (BookInfoEvent.OnShowTitleDialog) -> Unit
) {
    StyledText(
        text = book.title,
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable(
                onClick = {},
                onLongClick = {
                    showTitleDialog(BookInfoEvent.OnShowTitleDialog)
                }
            ),
        style = MaterialTheme.typography.headlineSmall.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        maxLines = 4
    )
}