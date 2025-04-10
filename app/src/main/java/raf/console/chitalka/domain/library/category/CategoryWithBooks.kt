/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf0707 for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.library.category

import androidx.compose.runtime.Immutable
import raf.console.chitalka.domain.library.book.SelectableBook
import raf.console.chitalka.domain.ui.UIText

@Immutable
data class CategoryWithBooks(
    val category: Category,
    val title: UIText,
    val books: List<SelectableBook>
)