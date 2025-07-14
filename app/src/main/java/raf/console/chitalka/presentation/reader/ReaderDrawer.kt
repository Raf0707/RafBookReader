/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.reader

import androidx.compose.runtime.Composable
import raf.console.chitalka.domain.reader.ReaderText.Chapter
import raf.console.chitalka.domain.util.Drawer
import raf.console.chitalka.ui.reader.ReaderEvent
import raf.console.chitalka.ui.reader.ReaderScreen
import raf.console.chitalka.domain.reader.Bookmark
import raf.console.chitalka.domain.reader.Note


@Composable
fun ReaderDrawer(
    drawer: Drawer?,
    chapters: List<Chapter>,
    bookmarks: List<Bookmark>,
    notes: List<Note>,
    currentChapter: Chapter?,
    currentChapterProgress: Float,
    scrollToChapter: (ReaderEvent.OnScrollToChapter) -> Unit,
    scrollToBookmark: (ReaderEvent.OnScroll) -> Unit,
    scrollToNote: (Note) -> Unit,
    onDeleteBookmark: (Bookmark) -> Unit,
    onDeleteNote: (Note) -> Unit,
    dismissDrawer: (ReaderEvent.OnDismissDrawer) -> Unit,
    onEvent: (ReaderEvent) -> Any,
) {
    ReaderChaptersDrawer(
        show = drawer == ReaderScreen.CHAPTERS_DRAWER,
        chapters = chapters,
        bookmarks = bookmarks,
        notes = notes,
        currentChapter = currentChapter,
        currentChapterProgress = currentChapterProgress,
        scrollToChapter = scrollToChapter,
        scrollToBookmark = scrollToBookmark,
        scrollToNote = scrollToNote,
        onDeleteBookmark = onDeleteBookmark,
        onDeleteNote = onDeleteNote,
        dismissDrawer = dismissDrawer,
        onEvent = onEvent
    )
}