/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.browse

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import raf.console.chitalka.R
import raf.console.chitalka.domain.library.book.NullableBook
import raf.console.chitalka.domain.library.book.SelectableNullableBook
import raf.console.chitalka.presentation.core.components.dialog.Dialog
import raf.console.chitalka.presentation.core.components.progress_indicator.CircularProgressIndicator
import raf.console.chitalka.presentation.core.util.showToast
import raf.console.chitalka.ui.browse.BrowseEvent
import java.util.UUID

@Composable
fun BrowseAddDialog(
    loadingAddDialog: Boolean,
    selectedBooksAddDialog: List<SelectableNullableBook>,
    dismissAddDialog: (BrowseEvent.OnDismissAddDialog) -> Unit,
    actionAddDialog: (BrowseEvent.OnActionAddDialog) -> Unit,
    selectAddDialog: (BrowseEvent.OnSelectAddDialog) -> Unit,
    navigateToLibrary: () -> Unit
) {
    val context = LocalContext.current
    Dialog(
        title = stringResource(id = R.string.add_books),
        icon = Icons.Default.AddChart,
        description = stringResource(id = R.string.add_books_description),
        actionEnabled = !loadingAddDialog && selectedBooksAddDialog.any { it.data is NullableBook.NotNull },
        onDismiss = { dismissAddDialog(BrowseEvent.OnDismissAddDialog) },
        onAction = {
            actionAddDialog(
                BrowseEvent.OnActionAddDialog(
                    context = context,
                    navigateToLibrary = navigateToLibrary
                )
            )
        },
        withContent = true,
        items = {
            if (loadingAddDialog) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(36.dp)
                        )
                    }
                }
            } else {
                items(
                    selectedBooksAddDialog,
                    key = { it.data.fileName ?: UUID.randomUUID() }
                ) { book ->
                    BrowseAddDialogItem(
                        result = book
                    ) {
                        if (it) {
                            selectAddDialog(
                                BrowseEvent.OnSelectAddDialog(
                                    book = book
                                )
                            )
                        } else {
                            book.data.message?.asString(context)
                                ?.showToast(context = context)
                        }
                    }
                }
            }
        }
    )
}