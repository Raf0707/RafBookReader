/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.ui.reader

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Immutable
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.domain.reader.Checkpoint
import raf.console.chitalka.domain.reader.ReaderText
import raf.console.chitalka.domain.reader.ReaderText.Chapter
import raf.console.chitalka.domain.ui.UIText
import raf.console.chitalka.domain.util.BottomSheet
import raf.console.chitalka.domain.util.Drawer
import raf.console.chitalka.presentation.core.constants.provideEmptyBook

@Immutable
data class ReaderState(
    val book: Book = provideEmptyBook(),
    val text: List<ReaderText> = emptyList(),
    val listState: LazyListState = LazyListState(),

    val currentChapter: Chapter? = null,
    val currentChapterProgress: Float = 0f,

    val errorMessage: UIText? = null,
    val isLoading: Boolean = true,

    val showMenu: Boolean = false,
    val checkpoint: Checkpoint = Checkpoint(0, 0),
    val lockMenu: Boolean = false,

    val bottomSheet: BottomSheet? = null,
    val drawer: Drawer? = null
)