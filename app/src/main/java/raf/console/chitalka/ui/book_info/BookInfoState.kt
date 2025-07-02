/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.ui.book_info

import androidx.compose.runtime.Immutable
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.domain.util.BottomSheet
import raf.console.chitalka.domain.util.Dialog
import raf.console.chitalka.presentation.core.constants.provideEmptyBook

@Immutable
data class BookInfoState(
    val book: Book = provideEmptyBook(),

    val canResetCover: Boolean = false,

    val dialog: Dialog? = null,
    val bottomSheet: BottomSheet? = null
)