/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.book_info

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.presentation.core.components.dialog.DialogWithTextField
import raf.console.chitalka.ui.book_info.BookInfoEvent

@Composable
fun BookInfoPathDialog(
    book: Book,
    actionPathDialog: (BookInfoEvent.OnActionPathDialog) -> Unit,
    dismissDialog: (BookInfoEvent.OnDismissDialog) -> Unit
) {
    val context = LocalContext.current
    DialogWithTextField(
        initialValue = book.filePath.trim(),
        lengthLimit = 10000,
        onDismiss = {
            dismissDialog(BookInfoEvent.OnDismissDialog)
        },
        onAction = {
            if (it.isBlank()) return@DialogWithTextField
            actionPathDialog(
                BookInfoEvent.OnActionPathDialog(
                    path = it.trim().replace("\n", ""),
                    context = context
                )
            )
        }
    )
}