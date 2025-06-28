/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.book_info

import androidx.compose.runtime.Composable
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.domain.util.Dialog
import raf.console.chitalka.ui.book_info.BookInfoEvent
import raf.console.chitalka.ui.book_info.BookInfoScreen
import raf.console.chitalka.domain.library.custom_category.Category

@Composable
fun BookInfoDialog(
    dialog: Dialog?,
    book: Book,
    actionTitleDialog: (BookInfoEvent.OnActionTitleDialog) -> Unit,
    actionAuthorDialog: (BookInfoEvent.OnActionAuthorDialog) -> Unit,
    actionDescriptionDialog: (BookInfoEvent.OnActionDescriptionDialog) -> Unit,
    actionPathDialog: (BookInfoEvent.OnActionPathDialog) -> Unit,
    actionDeleteDialog: (BookInfoEvent.OnActionDeleteDialog) -> Unit,
    actionSetCategoriesDialog: (BookInfoEvent.OnActionSetCategoriesDialog) -> Unit,
    categories: List<raf.console.chitalka.domain.library.custom_category.Category>,
    dismissDialog: (BookInfoEvent.OnDismissDialog) -> Unit,
    navigateBack: () -> Unit,
    navigateToLibrary: () -> Unit
) {
    when (dialog) {
        BookInfoScreen.DELETE_DIALOG -> {
            BookInfoDeleteDialog(
                actionDeleteDialog = actionDeleteDialog,
                dismissDialog = dismissDialog,
                navigateBack = navigateBack
            )
        }

        BookInfoScreen.TITLE_DIALOG -> {
            BookInfoTitleDialog(
                book = book,
                actionTitleDialog = actionTitleDialog,
                dismissDialog = dismissDialog
            )
        }

        BookInfoScreen.AUTHOR_DIALOG -> {
            BookInfoAuthorDialog(
                book = book,
                actionAuthorDialog = actionAuthorDialog,
                dismissDialog = dismissDialog
            )
        }

        BookInfoScreen.DESCRIPTION_DIALOG -> {
            BookInfoDescriptionDialog(
                book = book,
                actionDescriptionDialog = actionDescriptionDialog,
                dismissDialog = dismissDialog
            )
        }

        BookInfoScreen.PATH_DIALOG -> {
            BookInfoPathDialog(
                book = book,
                actionPathDialog = actionPathDialog,
                dismissDialog = dismissDialog
            )
        }

        BookInfoScreen.CATEGORIES_DIALOG -> {
            BookInfoCategoriesDialog(
                currentCategoryIds = book.categoryIds,
                categories = categories,
                onAction = actionSetCategoriesDialog,
                onDismiss = { dismissDialog(BookInfoEvent.OnDismissDialog) }
            )
        }
    }
}