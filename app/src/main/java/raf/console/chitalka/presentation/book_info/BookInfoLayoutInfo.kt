/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.book_info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.ui.book_info.BookInfoEvent

@Composable
fun BookInfoLayoutInfo(
    book: Book,
    showTitleDialog: (BookInfoEvent.OnShowTitleDialog) -> Unit,
    showAuthorDialog: (BookInfoEvent.OnShowAuthorDialog) -> Unit,
    showChangeCoverBottomSheet: (BookInfoEvent.OnShowChangeCoverBottomSheet) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BookInfoLayoutInfoCover(
            book = book,
            showChangeCoverBottomSheet = showChangeCoverBottomSheet
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            BookInfoLayoutInfoTitle(
                book = book,
                showTitleDialog = showTitleDialog
            )

            BookInfoLayoutInfoAuthor(
                book = book,
                showAuthorDialog = showAuthorDialog
            )

            BookInfoLayoutInfoProgress(
                book = book
            )
        }
    }
}