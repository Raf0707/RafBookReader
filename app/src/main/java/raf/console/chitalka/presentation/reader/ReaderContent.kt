/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.reader

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.hilt.navigation.compose.hiltViewModel
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
    onShowNotesDrawer: (ReaderEvent.OnShowNotesBookmarksDrawer) -> Unit,
    onStartTTS: () -> Unit,
    selectedTranslator: TranslatorApp,
    bookmarks: List<Bookmark> = emptyList(),
    notes: List<Note> = emptyList(),
    onEvent: (ReaderEvent) -> Unit
) {
    val chaptersDrawerState = rememberDrawerState(DrawerValue.Closed)
    val notesDrawerState = rememberDrawerState(DrawerValue.Closed)

    val readerModel: ReaderModel = hiltViewModel()

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
                currentChapter = currentChapter,
                currentChapterProgress = currentChapterProgress,
                scrollToChapter = scrollToChapter,
                dismissDrawer = dismissDrawer
            )
        }
    ) {
        ModalNavigationDrawer(
            drawerState = notesDrawerState,
            drawerContent = {

                val scope = rememberCoroutineScope()

                ReaderNotesBookmarksDrawer(
                    show = notesDrawerState.isOpen,
                    bookmarks = bookmarks,
                    notes = notes,
                    dismissDrawer = dismissDrawer,
                    onEvent = {
                        when (it) {
                            is ReaderEvent.OnScrollToBookmark -> {
                                // Сначала передаём событие в ViewModel для обновления checkpoint и highlightedText
                                readerModel.onEvent(it)

                                // Затем прокручиваем UI
                                scope.launch {
                                    // ⏱ подождём чуть-чуть, чтобы ViewModel успел обновиться
                                    kotlinx.coroutines.delay(100)
                                    listState.scrollToItem(it.offset.toInt())
                                }
                            }

                            else -> readerModel.onEvent(it)
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
                        onShowNotesDrawer(ReaderEvent.OnShowNotesBookmarksDrawer(book.id.toLong()))
                    },
                    onStartTTS = onStartTTS,
                    selectedTranslator = selectedTranslator,
                    onEvent = onEvent
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
}
