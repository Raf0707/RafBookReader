/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.book_info

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.presentation.core.components.dialog.DialogWithTextField
import raf.console.chitalka.ui.book_info.BookInfoEvent

@Composable
fun BookInfoDescriptionDialog(
    book: Book,
    actionDescriptionDialog: (BookInfoEvent.OnActionDescriptionDialog) -> Unit,
    dismissDialog: (BookInfoEvent.OnDismissDialog) -> Unit
) {
    val context = LocalContext.current
    DialogWithTextField(
        initialValue = book.description ?: "",
        lengthLimit = 5000,
        onDismiss = {
            dismissDialog(BookInfoEvent.OnDismissDialog)
        },
        onAction = {
            actionDescriptionDialog(
                BookInfoEvent.OnActionDescriptionDialog(
                    description = if (it.isBlank()) null
                    else it.trim().replace("\n", ""),
                    context = context
                )
            )
        }
    )
}