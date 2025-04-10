/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf0707 for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.book_info

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.dialog.Dialog
import raf.console.chitalka.ui.book_info.BookInfoEvent

@Composable
fun BookInfoDeleteDialog(
    actionDeleteDialog: (BookInfoEvent.OnActionDeleteDialog) -> Unit,
    dismissDialog: (BookInfoEvent.OnDismissDialog) -> Unit,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current

    Dialog(
        title = stringResource(id = R.string.delete_book),
        icon = Icons.Outlined.DeleteOutline,
        description = stringResource(
            id = R.string.delete_book_description
        ),
        onDismiss = { dismissDialog(BookInfoEvent.OnDismissDialog) },
        withContent = false,
        actionEnabled = true,
        onAction = {
            actionDeleteDialog(
                BookInfoEvent.OnActionDeleteDialog(
                    context = context,
                    navigateBack = navigateBack
                )
            )
        }
    )
}