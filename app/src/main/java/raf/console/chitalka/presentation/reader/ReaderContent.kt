/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.reader

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.domain.reader.Bookmark
import raf.console.chitalka.domain.reader.Checkpoint
import raf.console.chitalka.domain.reader.FontWithName
import raf.console.chitalka.domain.reader.Note
import raf.console.chitalka.domain.reader.ReaderFontThickness
import raf.console.chitalka.domain.reader.ReaderHorizontalGesture
import raf.console.chitalka.domain.reader.ReaderText
import raf.console.chitalka.domain.reader.ReaderText.Chapter
import raf.console.chitalka.domain.reader.ReaderTextAlignment
import raf.console.chitalka.domain.ui.UIText
import raf.console.chitalka.domain.util.BottomSheet
import raf.console.chitalka.domain.util.Drawer
import raf.console.chitalka.domain.util.HorizontalAlignment
import raf.console.chitalka.presentation.reader.translator.TranslatorApp
import raf.console.chitalka.ui.reader.ReaderEvent
import raf.console.chitalka.ui.reader.ReaderModel
import raf.console.chitalka.ui.reader.ReaderScreen
import raf.console.chitalka.ui.settings.SettingsEvent

@Composable
fun ReaderContent(
    book: Book,
    text: List<ReaderText>,
    bottomSheet: BottomSheet?,
    drawer: Drawer?,
    listState: LazyListState,
    currentChapter: Chapter?,
    nestedScrollConnection: NestedScrollConnection,
    fastColorPresetChange: Boolean,
    perceptionExpander: Boolean,
    perceptionExpanderPadding: Dp,
    perceptionExpanderThickness: Dp,
    currentChapterProgress: Float,
    isLoading: Boolean,
    errorMessage: UIText?,
    checkpoint: Checkpoint,
    showMenu: Boolean,
    lockMenu: Boolean,
    contentPadding: PaddingValues,
    verticalPadding: Dp,
    horizontalGesture: ReaderHorizontalGesture,
    horizontalGestureScroll: Float,
    horizontalGestureSensitivity: Dp,
    horizontalGestureAlphaAnim: Boolean,
    horizontalGesturePullAnim: Boolean,
    highlightedReading: Boolean,
    highlightedReadingThickness: FontWeight,
    progress: String,
    progressBar: Boolean,
    progressBarPadding: Dp,
    progressBarAlignment: HorizontalAlignment,
    progressBarFontSize: TextUnit,
    paragraphHeight: Dp,
    sidePadding: Dp,
    bottomBarPadding: Dp,
    backgroundColor: Color,
    fontColor: Color,
    images: Boolean,
    imagesCornersRoundness: Dp,
    imagesAlignment: HorizontalAlignment,
    imagesWidth: Float,
    imagesColorEffects: ColorFilter?,
    fontFamily: FontWithName,
    lineHeight: TextUnit,
    fontThickness: ReaderFontThickness,
    fontStyle: FontStyle,
    chapterTitleAlignment: ReaderTextAlignment,
    textAlignment: ReaderTextAlignment,
    horizontalAlignment: Alignment.Horizontal,
    fontSize: TextUnit,
    letterSpacing: TextUnit,
    paragraphIndentation: TextUnit,
    doubleClickTranslation: Boolean,
    fullscreenMode: Boolean,
    selectPreviousPreset: (SettingsEvent.OnSelectPreviousPreset) -> Unit,
    selectNextPreset: (SettingsEvent.OnSelectNextPreset) -> Unit,
    menuVisibility: (ReaderEvent.OnMenuVisibility) -> Unit,
    leave: (ReaderEvent.OnLeave) -> Unit,
    restoreCheckpoint: (ReaderEvent.OnRestoreCheckpoint) -> Unit,
    scroll: (ReaderEvent.OnScroll) -> Unit,
    changeProgress: (ReaderEvent.OnChangeProgress) -> Unit,
    openShareApp: (ReaderEvent.OnOpenShareApp) -> Unit,
    openWebBrowser: (ReaderEvent.OnOpenWebBrowser) -> Unit,
    openTranslator: (ReaderEvent.OnOpenTranslator) -> Unit,
    openDictionary: (ReaderEvent.OnOpenDictionary) -> Unit,
    scrollToChapter: (ReaderEvent.OnScrollToChapter) -> Unit,
    showSettingsBottomSheet: (ReaderEvent.OnShowSettingsBottomSheet) -> Unit,
    dismissBottomSheet: (ReaderEvent.OnDismissBottomSheet) -> Unit,
    showChaptersDrawer: (ReaderEvent.OnShowChaptersDrawer) -> Unit,
    dismissDrawer: (ReaderEvent.OnDismissDrawer) -> Unit,
    navigateToBookInfo: (changePath: Boolean) -> Unit,
    navigateBack: () -> Unit,
    onShowNotesDrawer: (ReaderEvent.OnShowChaptersDrawer) -> Unit,
    onStartTTS: () -> Unit,
    selectedTranslator: TranslatorApp,
    bookmarks: List<Bookmark> = emptyList(),
    notes: List<Note> = emptyList(),
    onEvent: (ReaderEvent) -> Unit,
    highlightedText: String?,
    readerModel: ReaderModel
) {
    val chaptersDrawerState = rememberDrawerState(DrawerValue.Closed)
    val notesDrawerState = rememberDrawerState(DrawerValue.Closed)

    //val readerModel: ReaderModel = hiltViewModel()

    val scope = rememberCoroutineScope()

    var showCreateNoteDialog by remember { mutableStateOf(false) }

    var noteContent by remember { mutableStateOf("") }

    val state by readerModel.state.collectAsState()

    LaunchedEffect(drawer) {
        when (drawer) {
            ReaderScreen.CHAPTERS_DRAWER -> {
                launch { notesDrawerState.snapTo(DrawerValue.Closed) }
                launch { chaptersDrawerState.snapTo(DrawerValue.Open) }
            }
            ReaderScreen.NOTES_BOOKMARKS_DRAWER -> {
                launch { chaptersDrawerState.snapTo(DrawerValue.Closed) }
                launch { notesDrawerState.snapTo(DrawerValue.Open) }
            }
            else -> {
                launch { chaptersDrawerState.snapTo(DrawerValue.Closed) }
                launch { notesDrawerState.snapTo(DrawerValue.Closed) }
            }
        }
    }



    ModalNavigationDrawer(
        drawerState = chaptersDrawerState,
        drawerContent = {
            ReaderChaptersDrawer(
                show = chaptersDrawerState.isOpen,
                chapters = text.filterIsInstance<Chapter>(),
                bookmarks = bookmarks,
                notes = state.notes,
                currentChapter = currentChapter,
                currentChapterProgress = currentChapterProgress,
                scrollToChapter = scrollToChapter,
                dismissDrawer = dismissDrawer,
                /*scrollToBookmark = { bookmark ->
                    scope.launch {
                        delay(100)
                        val targetProgress = bookmark.progress ?: 0f
                        readerModel.onEvent(
                            ReaderEvent.OnScroll(targetProgress)
                        )
                        dismissDrawer(ReaderEvent.OnDismissDrawer)
                    }
                },*/
                scrollToBookmark = { bookmark ->
                    scope.launch {
                        delay(100)
                        val progress = bookmark.progress ?: 0f
                        val index = (progress * listState.layoutInfo.totalItemsCount).toInt().coerceAtLeast(0)
                        listState.animateScrollToItem(index)
                        dismissDrawer(ReaderEvent.OnDismissDrawer)
                    }
                },
                scrollToNote = { note: Note ->
                    scope.launch {
                        delay(100)
                        val targetIndex = readerModel.findGlobalIndexForBookmark(
                            chapterIndex = note.chapterIndex.toInt(),
                            offset = note.offsetStart.toInt()
                        )
                        if (targetIndex >= 0) {
                            listState.animateScrollToItem(targetIndex)
                        }
                    }
                },
                onDeleteBookmark = { bookmark ->
                    onEvent(ReaderEvent.OnDeleteBookmark(bookmark))
                },
                onDeleteNote = { note ->
                    onEvent(ReaderEvent.OnDeleteNote(note))
                },
                onEvent = { event: ReaderEvent ->
                    when (event) {
                        is ReaderEvent.OnScrollToBookmark -> {
                            readerModel.onEvent(event)
                            scope.launch {
                                delay(100)
                                val targetIndex = readerModel.findGlobalIndexForBookmark(
                                    chapterIndex = event.chapterIndex,
                                    offset = event.offset.toInt()
                                )
                                if (targetIndex >= 0) {
                                    listState.animateScrollToItem(targetIndex)
                                }
                            }
                        }
                        is ReaderEvent.OnShowCreateNoteDialog -> {
                            showCreateNoteDialog = true
                        }
                        else -> readerModel.onEvent(event)
                    }
                } as (ReaderEvent) -> Any


            )
        }
    )
    {
        ModalNavigationDrawer(
            drawerState = notesDrawerState,
            drawerContent = {

                val scope = rememberCoroutineScope()

                ReaderNotesBookmarksDrawer(
                    show = notesDrawerState.isOpen,
                    bookmarks = bookmarks,
                    notes = state.notes,
                    dismissDrawer = dismissDrawer,
                    onEvent = { event ->
                        when (event) {
                            is ReaderEvent.OnScrollToBookmark -> {
                                readerModel.onEvent(event)
                                scope.launch {
                                    delay(100)
                                    val targetIndex = readerModel.findGlobalIndexForBookmark(
                                        chapterIndex = event.chapterIndex,
                                        offset = event.offset.toInt()
                                    )
                                    if (targetIndex >= 0) {
                                        listState.animateScrollToItem(targetIndex)
                                    }
                                }
                            }
                            is ReaderEvent.OnShowCreateNoteDialog -> {
                                showCreateNoteDialog = true
                            }
                            else -> readerModel.onEvent(event)
                        }
                    },
                    listState = listState
                )
            }
        ) {
            // Main content
            ReaderBottomSheet(
                bottomSheet = bottomSheet,
                fullscreenMode = fullscreenMode,
                menuVisibility = menuVisibility,
                dismissBottomSheet = dismissBottomSheet
            )

            if (isLoading || errorMessage == null) {
                ReaderScaffold(
                    book = book,
                    text = text,
                    listState = listState,
                    currentChapter = currentChapter,
                    nestedScrollConnection = nestedScrollConnection,
                    fastColorPresetChange = fastColorPresetChange,
                    perceptionExpander = perceptionExpander,
                    perceptionExpanderPadding = perceptionExpanderPadding,
                    perceptionExpanderThickness = perceptionExpanderThickness,
                    currentChapterProgress = currentChapterProgress,
                    isLoading = isLoading,
                    checkpoint = checkpoint,
                    showMenu = showMenu,
                    lockMenu = lockMenu,
                    contentPadding = contentPadding,
                    verticalPadding = verticalPadding,
                    horizontalGesture = horizontalGesture,
                    horizontalGestureScroll = horizontalGestureScroll,
                    horizontalGestureSensitivity = horizontalGestureSensitivity,
                    horizontalGestureAlphaAnim = horizontalGestureAlphaAnim,
                    horizontalGesturePullAnim = horizontalGesturePullAnim,
                    highlightedReading = highlightedReading,
                    highlightedReadingThickness = highlightedReadingThickness,
                    progress = progress,
                    progressBar = progressBar,
                    progressBarPadding = progressBarPadding,
                    progressBarAlignment = progressBarAlignment,
                    progressBarFontSize = progressBarFontSize,
                    paragraphHeight = paragraphHeight,
                    sidePadding = sidePadding,
                    bottomBarPadding = bottomBarPadding,
                    backgroundColor = backgroundColor,
                    fontColor = fontColor,
                    images = images,
                    imagesCornersRoundness = imagesCornersRoundness,
                    imagesAlignment = imagesAlignment,
                    imagesWidth = imagesWidth,
                    imagesColorEffects = imagesColorEffects,
                    fontFamily = fontFamily,
                    lineHeight = lineHeight,
                    fontThickness = fontThickness,
                    fontStyle = fontStyle,
                    chapterTitleAlignment = chapterTitleAlignment,
                    textAlignment = textAlignment,
                    horizontalAlignment = horizontalAlignment,
                    fontSize = fontSize,
                    letterSpacing = letterSpacing,
                    paragraphIndentation = paragraphIndentation,
                    doubleClickTranslation = doubleClickTranslation,
                    fullscreenMode = fullscreenMode,
                    selectPreviousPreset = selectPreviousPreset,
                    selectNextPreset = selectNextPreset,
                    menuVisibility = menuVisibility,
                    leave = leave,
                    restoreCheckpoint = restoreCheckpoint,
                    scroll = scroll,
                    changeProgress = changeProgress,
                    openShareApp = openShareApp,
                    openWebBrowser = openWebBrowser,
                    openTranslator = openTranslator,
                    openDictionary = openDictionary,
                    showSettingsBottomSheet = showSettingsBottomSheet,
                    showChaptersDrawer = showChaptersDrawer,
                    navigateBack = navigateBack,
                    navigateToBookInfo = navigateToBookInfo,
                    OnShowNotesBookmarksDrawer = {
                        onShowNotesDrawer(ReaderEvent.OnShowChaptersDrawer)
                    },
                    onStartTTS = onStartTTS,
                    selectedTranslator = selectedTranslator,
                    highlightedText = highlightedText,
                    onEvent = { event ->
                        when (event) {
                            is ReaderEvent.OnScrollToBookmark -> {
                                readerModel.onEvent(event)
                                scope.launch {
                                    delay(100)
                                    val targetIndex = readerModel.findGlobalIndexForBookmark(
                                        chapterIndex = event.chapterIndex,
                                        offset = event.offset.toInt()
                                    )
                                    if (targetIndex >= 0) {
                                        listState.animateScrollToItem(targetIndex)
                                    }
                                }
                            }
                            is ReaderEvent.OnShowCreateNoteDialog -> {
                                showCreateNoteDialog = true
                            }
                            else -> readerModel.onEvent(event)
                        }
                    },
                )
            } else {
                ReaderErrorPlaceholder(
                    errorMessage = errorMessage,
                    leave = leave,
                    navigateToBookInfo = navigateToBookInfo,
                    navigateBack = navigateBack
                )
            }

            ReaderBackHandler(
                leave = leave,
                navigateBack = navigateBack
            )
        }
    }
    if (showCreateNoteDialog) {
        AlertDialog(
            onDismissRequest = { showCreateNoteDialog = false },
            title = { Text("Новая заметка") },
            text = {
                TextField(
                    value = noteContent,
                    onValueChange = { noteContent = it },
                    placeholder = { Text("Введите текст заметки") }
                )
            },
            confirmButton = {
                val visibleItemIndex = listState.firstVisibleItemIndex
                val visibleItemOffset = listState.firstVisibleItemScrollOffset
                val chapterIndex = readerModel.findChapterIndexForGlobalIndex(text, visibleItemIndex)

                /*TextButton(onClick = {
                    onEvent(
                        ReaderEvent.OnAddNote(
                            bookId = book.id.toLong(),
                            content = noteContent.trim(),
                            chapterIndex = readerModel.findChapterIndexForGlobalIndex(text, listState.firstVisibleItemIndex).toLong(),
                            offsetStart = 0L, // Можешь заменить на реальное смещение в тексте
                            offsetEnd = 0L    // Если не поддерживается range selection — можно дублировать offsetStart
                        )
                    )
                    showCreateNoteDialog = false
                    noteContent = ""
                }) {
                    Text("Сохранить")
                }*/
                TextButton(onClick = {
                    val content = noteContent.trim()
                    if (content.isNotEmpty()) {
                        println("✅ Saving note for bookId=${book.id}")
                        onEvent(
                            ReaderEvent.OnAddNote(
                                bookId = book.id.toLong(),
                                content = content,
                                chapterIndex = chapterIndex.toLong(),
                                offsetStart = 0L,
                                offsetEnd = 0L
                            )
                        )
                        showCreateNoteDialog = false
                        noteContent = ""
                    }
                }) {
                    Text("Сохранить")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showCreateNoteDialog = false
                }) {
                    Text("Отмена")
                }
            }
        )
    }
}




/*fun findGlobalIndexForBookmarkLocal(
    text: List<ReaderText>,
    chapterIndex: Int,
    offset: Int
): Int {
    var index = 0
    var chapterCount = -1

    while (index < text.size) {
        val item = text[index]
        if (item is ReaderText.Chapter) {
            chapterCount++
            if (chapterCount == chapterIndex) {
                // Найдена нужная глава
                var offsetCount = 0
                var searchIndex = index + 1
                while (searchIndex < text.size) {
                    val current = text[searchIndex]
                    if (current is ReaderText.Text) {
                        if (offsetCount == offset) {
                            return searchIndex
                        }
                        offsetCount++
                    }
                    if (current is ReaderText.Chapter) {
                        break
                    }
                    searchIndex++
                }
                return searchIndex.coerceAtMost(text.lastIndex)
            }
        }
        index++
    }
    return 0
}*/

