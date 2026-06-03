/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.reader

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import raf.console.chitalka.R
import raf.console.chitalka.domain.reader.Bookmark
import raf.console.chitalka.domain.reader.ExpandableChapter
import raf.console.chitalka.domain.reader.Note
import raf.console.chitalka.domain.reader.ReaderText.Chapter
import raf.console.chitalka.presentation.core.components.common.StyledText
import raf.console.chitalka.presentation.core.components.modal_drawer.ModalDrawer
import raf.console.chitalka.presentation.core.components.modal_drawer.ModalDrawerSelectableItem
import raf.console.chitalka.presentation.core.components.modal_drawer.ModalDrawerTitleItem
import raf.console.chitalka.presentation.core.util.calculateProgress
import raf.console.chitalka.presentation.core.util.noRippleClickable
import raf.console.chitalka.ui.reader.ReaderEvent
import raf.console.chitalka.ui.reader.ReaderModel
import raf.console.chitalka.ui.theme.ExpandingTransition


@Composable
fun ReaderChaptersDrawer(
    show: Boolean,
    chapters: List<Chapter>,
    bookmarks: List<Bookmark>,
    notes: List<Note>,
    currentChapter: Chapter?,
    currentChapterProgress: Float,
    scrollToChapter: (ReaderEvent.OnScrollToChapter) -> Unit,
    //scrollToBookmark: (ReaderEvent.OnScrollToBookmark) -> Unit,
    scrollToBookmark: (ReaderEvent.OnScroll) -> Unit,
    //scrollToNote: (ReaderEvent.OnJumpToNote) -> Unit,
    onEvent: (ReaderEvent) -> Any,
    scrollToNote: (Note) -> Unit,
    dismissDrawer: (ReaderEvent.OnDismissDrawer) -> Unit,
    onDeleteBookmark: (Bookmark) -> Unit,
    onDeleteNote: (Note) -> Unit,
    readerModel: ReaderModel
) {
    val sections = listOf("Содержание", "Закладки", "Заметки")
    var selectedSection by remember { mutableStateOf(0) }

    var allBookmarksExpanded by remember { mutableStateOf(false) }
    val bookmarkExpandedState = remember { mutableStateListOf<Boolean>() }
    if (bookmarkExpandedState.size != bookmarks.size) {
        bookmarkExpandedState.clear()
        bookmarkExpandedState.addAll(List(bookmarks.size) { allBookmarksExpanded })
    }

    var allNotesExpanded by remember { mutableStateOf(true) }
    val noteExpandedState = remember { mutableStateListOf<Boolean>() }
    if (noteExpandedState.size != notes.size) {
        noteExpandedState.clear()
        noteExpandedState.addAll(List(notes.size) { allNotesExpanded })
    }

    // В начале файла, в ReaderChaptersDrawer:
    /*val bookmarkExpandedStates = remember(bookmarks) {
        mutableStateListOf<Boolean>().apply { repeat(bookmarks.size) { add(false) } }
    }*/
    //val bookmarksAllExpanded = remember { mutableStateOf(false) }

    /*val noteExpandedStates = remember(notes) {
        mutableStateListOf<Boolean>().apply { repeat(notes.size) { add(true) } }
    }*/
    //val notesAllExpanded = remember { mutableStateOf(true) }

    var bookmarksAllExpanded = readerModel.bookmarksAllExpanded
    val bookmarkExpandedStates = readerModel.bookmarkExpandedStates

    var notesAllExpanded = readerModel.notesAllExpanded
    val noteExpandedStates = readerModel.noteExpandedStates

    val areAllBookmarksExpanded by remember {
        derivedStateOf { bookmarkExpandedStates.all { it } }
    }
    val areAllNotesExpanded by remember {
        derivedStateOf { noteExpandedStates.all { it } }
    }


    fun syncExpandedStates(currentBookmarks: List<Bookmark>, currentNotes: List<Note>) {
        if (bookmarkExpandedStates.size != currentBookmarks.size) {
            bookmarkExpandedStates.clear()
            repeat(currentBookmarks.size) {
                bookmarkExpandedStates.add(bookmarksAllExpanded)
            }
        }
        if (noteExpandedStates.size != currentNotes.size) {
            noteExpandedStates.clear()
            repeat(currentNotes.size) {
                noteExpandedStates.add(notesAllExpanded)
            }
        }
    }


    val expandableChapters = remember(show, chapters, currentChapter) {
        mutableStateListOf<ExpandableChapter>().apply {
            var index = 0
            while (index < chapters.size) {
                val chapter = chapters.getOrNull(index) ?: continue
                when (chapter.nested) {
                    false -> {
                        val children = chapters.drop(index + 1).takeWhile { it.nested }
                        add(
                            ExpandableChapter(
                                parent = chapter,
                                expanded = chapter.id == currentChapter?.id ||
                                        children.any { it.id == currentChapter?.id },
                                chapters = children.takeIf { it.isNotEmpty() }
                            )
                        )
                        index += children.size + 1
                    }

                    true -> {
                        add(
                            ExpandableChapter(
                                parent = chapter.copy(nested = false),
                                expanded = false,
                                chapters = null
                            )
                        )
                        index++
                    }
                }
            }
        }
    }

    ModalDrawer(
        show = show,
        onDismissRequest = { dismissDrawer(ReaderEvent.OnDismissDrawer) },
        header = {
            Column {
                ModalDrawerTitleItem(title = "Навигация")
                ScrollableTabRow(
                    selectedTabIndex = selectedSection,
                    edgePadding = 8.dp // Можно уменьшить отступы
                ) {
                    sections.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedSection == index,
                            onClick = { selectedSection = index },
                            text = { Text(title, maxLines = 1) }
                        )
                    }
                }
            }
        }
    ) {
        when (selectedSection) {
            0 -> { // Главы

                item {
                    OutlinedButton(
                        onClick = {
                            scrollToBookmark(ReaderEvent.OnScroll(1f))
                            dismissDrawer(ReaderEvent.OnDismissDrawer)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Text("В конец")
                    }
                }

                expandableChapters.forEach { expandableChapter ->

                    item {
                        ModalDrawerSelectableItem(
                            selected = expandableChapter.parent.id == currentChapter?.id,
                            onClick = {
                                scrollToChapter(
                                    ReaderEvent.OnScrollToChapter(
                                        chapter = expandableChapter.parent
                                    )
                                )
                                dismissDrawer(ReaderEvent.OnDismissDrawer)
                            }
                        ) {
                            StyledText(
                                text = expandableChapter.parent.title,
                                modifier = Modifier.weight(1f),
                                maxLines = 1
                            )

                            if (expandableChapter.parent == currentChapter) {
                                Spacer(modifier = Modifier.width(18.dp))
                                StyledText(text = "${currentChapterProgress.calculateProgress(0)}%")
                            }

                            if (!expandableChapter.chapters.isNullOrEmpty()) {
                                Spacer(modifier = Modifier.width(18.dp))
                                Icon(
                                    imageVector = Icons.Outlined.ArrowDropUp,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .noRippleClickable {
                                            expandableChapters.indexOf(expandableChapter)
                                                .also { chapterIndex ->
                                                    if (chapterIndex == -1) return@noRippleClickable
                                                    expandableChapters[chapterIndex] =
                                                        expandableChapter.copy(
                                                            expanded = !expandableChapter.expanded
                                                        )
                                                }
                                        }
                                        .rotate(
                                            animateFloatAsState(
                                                targetValue = if (expandableChapter.expanded) 0f else -180f
                                            ).value
                                        ),
                                    contentDescription = null
                                )
                            }
                        }
                    }

                    if (!expandableChapter.chapters.isNullOrEmpty()) {
                        items(expandableChapter.chapters) { chapter ->
                            ExpandingTransition(visible = expandableChapter.expanded) {
                                ModalDrawerSelectableItem(
                                    selected = chapter.id == currentChapter?.id,
                                    onClick = {
                                        scrollToChapter(ReaderEvent.OnScrollToChapter(chapter))
                                        dismissDrawer(ReaderEvent.OnDismissDrawer)
                                    }
                                ) {
                                    Spacer(modifier = Modifier.width(18.dp))
                                    StyledText(
                                        text = chapter.title,
                                        modifier = Modifier.weight(1f),
                                        maxLines = 1
                                    )

                                    if (chapter == currentChapter) {
                                        Spacer(modifier = Modifier.width(18.dp))
                                        StyledText(
                                            text = "${
                                                currentChapterProgress.calculateProgress(
                                                    0
                                                )
                                            }%"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    OutlinedButton(
                        onClick = {
                            scrollToBookmark(ReaderEvent.OnScroll(0f))
                            dismissDrawer(ReaderEvent.OnDismissDrawer)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Text("В начало")
                    }
                }
            }

            1 -> {
                if (bookmarks.isNotEmpty()) {
                    item {
                        OutlinedButton(
                            onClick = {
                                val expand = !bookmarksAllExpanded
                                bookmarksAllExpanded = expand
                                bookmarkExpandedStates.indices.forEach { bookmarkExpandedStates[it] = expand }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            Text("Раскрыть/Свернуть все")
                        }
                    }
                }

                if (bookmarks.isEmpty()) {
                    item {
                        StyledText(
                            text = "Нет закладок",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    itemsIndexed(bookmarks) { index, bookmark ->
                        if (bookmarkExpandedStates.getOrNull(index) == true) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .noRippleClickable {
                                        scrollToBookmark(ReaderEvent.OnScroll(bookmark.progress ?: 0f))
                                        dismissDrawer(ReaderEvent.OnDismissDrawer)
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = bookmark.label.orEmpty(),
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                IconButton(onClick = { onDeleteBookmark(bookmark) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Удалить закладку")
                                }
                                IconButton(onClick = {
                                    bookmarkExpandedStates[index] = false
                                }) {
                                    Icon(Icons.Outlined.ArrowDropUp, contentDescription = "Свернуть")
                                }
                            }
                        } else {
                            ModalDrawerSelectableItem(
                                selected = false,
                                onClick = {
                                    scrollToBookmark(ReaderEvent.OnScroll(bookmark.progress ?: 0f))
                                    dismissDrawer(ReaderEvent.OnDismissDrawer)
                                }
                            ) {
                                StyledText(
                                    text = bookmark.label.orEmpty(),
                                    modifier = Modifier.weight(1f),
                                    maxLines = 2
                                )
                                IconButton(onClick = { onDeleteBookmark(bookmark) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Удалить закладку")
                                }
                                IconButton(onClick = {
                                    bookmarkExpandedStates[index] = true
                                }) {
                                    Icon(Icons.Outlined.ArrowDropUp, contentDescription = "Раскрыть", modifier = Modifier.rotate(180f))
                                }
                            }
                        }
                        HorizontalDivider(modifier = Modifier.padding(start = 16.dp))
                    }
                }
            }


            2 -> {
                if (notes.isNotEmpty()) {
                    item {
                        OutlinedButton(
                            onClick = {
                                val expand = !notesAllExpanded
                                notesAllExpanded = expand
                                noteExpandedStates.indices.forEach { noteExpandedStates[it] = expand }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            Text("Раскрыть/Свернуть все")
                        }
                    }
                }

                if (notes.isEmpty()) {
                    item {
                        StyledText(
                            text = "Нет заметок",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    itemsIndexed(notes) { index, note ->
                        val clipboardManager = LocalClipboardManager.current

                        if (noteExpandedStates.getOrNull(index) == true) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .noRippleClickable {
                                        clipboardManager.setText(AnnotatedString(note.content))
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = note.content,
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                IconButton(onClick = { onEvent(ReaderEvent.OnDeleteNote(note)) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Удалить заметку")
                                }
                                IconButton(onClick = {
                                    noteExpandedStates[index] = false
                                }) {
                                    Icon(Icons.Outlined.ArrowDropUp, contentDescription = "Свернуть")
                                }
                            }
                        } else {
                            ModalDrawerSelectableItem(
                                selected = false,
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(note.content))
                                }
                            ) {
                                StyledText(
                                    text = note.content,
                                    modifier = Modifier.weight(1f),
                                    maxLines = 2
                                )
                                IconButton(onClick = { onEvent(ReaderEvent.OnDeleteNote(note)) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Удалить заметку")
                                }
                                IconButton(onClick = {
                                    noteExpandedStates[index] = true
                                }) {
                                    Icon(Icons.Outlined.ArrowDropUp, contentDescription = "Раскрыть", modifier = Modifier.rotate(180f))
                                }
                            }
                        }
                        HorizontalDivider(modifier = Modifier.padding(start = 16.dp))
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { onEvent(ReaderEvent.OnShowCreateNoteDialog) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Добавить заметку")
                        }
                    }
                }
            }
        }
    }
}