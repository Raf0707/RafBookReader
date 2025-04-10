/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf0707 for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.book_info

import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DriveFileMove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.domain.library.category.Category
import raf.console.chitalka.presentation.core.components.dialog.Dialog
import raf.console.chitalka.presentation.core.components.dialog.SelectableDialogItem
import raf.console.chitalka.ui.book_info.BookInfoEvent

@Composable
fun BookInfoMoveDialog(
    book: Book,
    actionMoveDialog: (BookInfoEvent.OnActionMoveDialog) -> Unit,
    dismissDialog: (BookInfoEvent.OnDismissDialog) -> Unit,
    navigateToLibrary: () -> Unit
) {
    val context = LocalContext.current

    val categories = remember {
        Category.entries.filter { book.category != it }
    }
    val selectedCategory = remember {
        mutableStateOf(categories[0])
    }

    Dialog(
        title = stringResource(id = R.string.move_book),
        icon = Icons.AutoMirrored.Outlined.DriveFileMove,
        description = stringResource(
            id = R.string.move_book_description
        ),
        actionEnabled = true,
        onDismiss = { dismissDialog(BookInfoEvent.OnDismissDialog) },
        onAction = {
            actionMoveDialog(
                BookInfoEvent.OnActionMoveDialog(
                    category = selectedCategory.value,
                    context = context,
                    navigateToLibrary = navigateToLibrary
                )
            )
        },
        withContent = true,
        items = {
            items(categories, key = { it.name }) {
                val category = when (it) {
                    Category.READING -> stringResource(id = R.string.reading_tab)
                    Category.ALREADY_READ -> stringResource(id = R.string.already_read_tab)
                    Category.PLANNING -> stringResource(id = R.string.planning_tab)
                    Category.DROPPED -> stringResource(id = R.string.dropped_tab)
                }

                SelectableDialogItem(
                    selected = it == selectedCategory.value,
                    title = category
                ) {
                    selectedCategory.value = it
                }
            }
        }
    )
}