/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.library

import androidx.compose.runtime.Composable
import raf.console.chitalka.domain.library.book.SelectableBook
import raf.console.chitalka.domain.library.category.CategoryWithBooks
import raf.console.chitalka.domain.util.Dialog
import raf.console.chitalka.ui.library.LibraryEvent
import raf.console.chitalka.ui.library.LibraryScreen

@Composable
fun LibraryDialog(
    dialog: Dialog?,
    books: List<SelectableBook>,
    categories: List<CategoryWithBooks>,
    selectedItemsCount: Int,
    actionMoveDialog: (LibraryEvent.OnActionMoveDialog) -> Unit,
    actionDeleteDialog: (LibraryEvent.OnActionDeleteDialog) -> Unit,
    dismissDialog: (LibraryEvent.OnDismissDialog) -> Unit
) {
    when (dialog) {
        LibraryScreen.MOVE_DIALOG -> {
            LibraryMoveDialog(
                books = books,
                categories = categories,
                selectedItemsCount = selectedItemsCount,
                actionMoveDialog = actionMoveDialog,
                dismissDialog = dismissDialog
            )
        }

        LibraryScreen.DELETE_DIALOG -> {
            LibraryDeleteDialog(
                selectedItemsCount = selectedItemsCount,
                actionDeleteDialog = actionDeleteDialog,
                dismissDialog = dismissDialog
            )
        }
    }
}