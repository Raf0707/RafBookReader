/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.book_info

import androidx.compose.runtime.Composable
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.domain.util.BottomSheet
import raf.console.chitalka.ui.book_info.BookInfoEvent
import raf.console.chitalka.ui.book_info.BookInfoScreen

@Composable
fun BookInfoBottomSheet(
    bottomSheet: BottomSheet?,
    book: Book,
    canResetCover: Boolean,
    showPathDialog: (BookInfoEvent.OnShowPathDialog) -> Unit,
    changeCover: (BookInfoEvent.OnChangeCover) -> Unit,
    resetCover: (BookInfoEvent.OnResetCover) -> Unit,
    deleteCover: (BookInfoEvent.OnDeleteCover) -> Unit,
    checkCoverReset: (BookInfoEvent.OnCheckCoverReset) -> Unit,
    dismissBottomSheet: (BookInfoEvent.OnDismissBottomSheet) -> Unit
) {
    when (bottomSheet) {
        BookInfoScreen.CHANGE_COVER_BOTTOM_SHEET -> {
            BookInfoChangeCoverBottomSheet(
                book = book,
                canResetCover = canResetCover,
                changeCover = changeCover,
                resetCover = resetCover,
                deleteCover = deleteCover,
                checkCoverReset = checkCoverReset,
                dismissBottomSheet = dismissBottomSheet
            )
        }

        BookInfoScreen.DETAILS_BOTTOM_SHEET -> {
            BookInfoDetailsBottomSheet(
                book = book,
                showPathDialog = showPathDialog,
                dismissBottomSheet = dismissBottomSheet
            )
        }
    }
}