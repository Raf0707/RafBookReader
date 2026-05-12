/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.ui.reader

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Immutable
import raf.console.chitalka.domain.reader.Bookmark
import raf.console.chitalka.domain.reader.Note
import raf.console.chitalka.domain.reader.ReaderText.Chapter

@Immutable
sealed class ReaderEvent {

    data class OnLoadText(
        val activity: ComponentActivity,
        val fullscreenMode: Boolean
    ) : ReaderEvent()

    data class OnMenuVisibility(
        val show: Boolean,
        val fullscreenMode: Boolean,
        val saveCheckpoint: Boolean,
        val activity: ComponentActivity
    ) : ReaderEvent()

    data class OnChangeProgress(
        val progress: Float,
        val firstVisibleItemIndex: Int,
        val firstVisibleItemOffset: Int
    ) : ReaderEvent()

    data class OnScrollToChapter(
        val chapter: Chapter
    ) : ReaderEvent()

    data class OnScroll(
        val progress: Float
    ) : ReaderEvent()

    data object OnRestoreCheckpoint : ReaderEvent()

    data class OnLeave(
        val activity: ComponentActivity,
        val navigate: () -> Unit
    ) : ReaderEvent()

    data class OnOpenTranslator(
        val textToTranslate: String,
        val translateWholeParagraph: Boolean,
        val activity: ComponentActivity
    ) : ReaderEvent()

    data class OnOpenShareApp(
        val textToShare: String,
        val activity: ComponentActivity
    ) : ReaderEvent()

    data class OnOpenWebBrowser(
        val textToSearch: String,
        val activity: ComponentActivity
    ) : ReaderEvent()

    data class OnOpenDictionary(
        val textToDefine: String,
        val activity: ComponentActivity
    ) : ReaderEvent()

    data class OnShowNotesBookmarksDrawer(val bookId: Long) : ReaderEvent()

    data object OnShowSettingsBottomSheet : ReaderEvent()

    data object OnDismissBottomSheet : ReaderEvent()

    data object OnShowChaptersDrawer : ReaderEvent()

    data object OnDismissDrawer : ReaderEvent()

    data class OnStartTextToSpeech(val context: Context) : ReaderEvent()

    data class OnAddBookmark(
        val bookId: Long,
        val text: String,              // Выделенный текст для label
        val chapterIndex: Long,        // Индекс главы (например, из currentChapter?.index)
        val offset: Long               // Смещение курсора (или позиция в тексте)
    ) : ReaderEvent()

    data class OnScrollToBookmark(
        val chapterIndex: Int,
        val offset: Long,
        val text: String,
        val progress: Float? = null // ✅ Добавляем сюда progress
    ) : ReaderEvent()

    data class OnDeleteBookmark(val bookmark: Bookmark) : ReaderEvent()

    data class OnAddNote(
        val bookId: Long,
        val chapterIndex: Long,
        val offsetStart: Long,
        val offsetEnd: Long,
        val content: String
    ) : ReaderEvent()

    object OnShowCreateNoteDialog : ReaderEvent()

    data class OnCopyNote(val note: Note) : ReaderEvent()

    data class OnJumpToNote(val note: Note) : ReaderEvent()

    data class OnDeleteNote(val note: Note) : ReaderEvent()

}