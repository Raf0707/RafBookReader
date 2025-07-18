/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.reader

import android.annotation.SuppressLint
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.domain.reader.Checkpoint
import raf.console.chitalka.domain.reader.FontWithName
import raf.console.chitalka.domain.reader.ReaderFontThickness
import raf.console.chitalka.domain.reader.ReaderHorizontalGesture
import raf.console.chitalka.domain.reader.ReaderText
import raf.console.chitalka.domain.reader.ReaderText.Chapter
import raf.console.chitalka.domain.reader.ReaderTextAlignment
import raf.console.chitalka.domain.util.HorizontalAlignment
import raf.console.chitalka.presentation.core.components.common.AnimatedVisibility
import raf.console.chitalka.presentation.reader.translator.TranslatorApp
import raf.console.chitalka.ui.reader.ReaderEvent
import raf.console.chitalka.ui.reader.ReaderModel
import raf.console.chitalka.ui.settings.SettingsEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReaderScaffold(
    book: Book,
    text: List<ReaderText>,
    listState: LazyListState,
    currentChapter: Chapter?,
    nestedScrollConnection: NestedScrollConnection,
    fastColorPresetChange: Boolean,
    perceptionExpander: Boolean,
    perceptionExpanderPadding: Dp,
    perceptionExpanderThickness: Dp,
    currentChapterProgress: Float,
    isLoading: Boolean,
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
    showSettingsBottomSheet: (ReaderEvent.OnShowSettingsBottomSheet) -> Unit,
    showChaptersDrawer: (ReaderEvent.OnShowChaptersDrawer) -> Unit,
    navigateToBookInfo: (changePath: Boolean) -> Unit,
    navigateBack: () -> Unit,
    onStartTTS: () -> Unit,
    OnShowNotesBookmarksDrawer: (ReaderEvent.OnShowNotesBookmarksDrawer) -> Unit,
    selectedTranslator: TranslatorApp,
    onEvent: (ReaderEvent) -> Unit, // ← добавлен
    highlightedText: String?,
    ) {
    Scaffold(
        Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            AnimatedVisibility(
                visible = showMenu,
                enter = slideInVertically { -it },
                exit = slideOutVertically { -it }
            ) {
                ReaderTopBar(
                    book = book,
                    currentChapter = currentChapter,
                    fastColorPresetChange = fastColorPresetChange,
                    currentChapterProgress = currentChapterProgress,
                    isLoading = isLoading,
                    lockMenu = lockMenu,
                    leave = leave,
                    selectPreviousPreset = selectPreviousPreset,
                    selectNextPreset = selectNextPreset,
                    showSettingsBottomSheet = showSettingsBottomSheet,
                    showChaptersDrawer = showChaptersDrawer,
                    navigateBack = navigateBack,
                    navigateToBookInfo = navigateToBookInfo,
                    onStartTTS = onStartTTS,
                    OnShowNotesBookmarksDrawer = {
                        OnShowNotesBookmarksDrawer(ReaderEvent.OnShowNotesBookmarksDrawer(book.id.toLong()))
                    }
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = showMenu,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
            ) {
                ReaderBottomBar(
                    book = book,
                    progress = progress,
                    text = text,
                    listState = listState,
                    lockMenu = lockMenu,
                    checkpoint = checkpoint,
                    bottomBarPadding = bottomBarPadding,
                    restoreCheckpoint = restoreCheckpoint,
                    scroll = scroll,
                    changeProgress = changeProgress
                )
            }
        }
    ) {
        /*val currentChapterIndex = text.indexOfFirst {
            it is ReaderText.Chapter && it.id == currentChapter?.id
        }.coerceAtLeast(0)*/
        val chapterOrdinalIndex = text
            .filterIsInstance<ReaderText.Chapter>()
            .indexOfFirst { it.id == currentChapter?.id }
            .coerceAtLeast(0)


        val scope = rememberCoroutineScope()
        val readerModel: ReaderModel = hiltViewModel()

        val onEventHandler: (ReaderEvent) -> Unit = { event ->
            when (event) {
                is ReaderEvent.OnScrollToBookmark -> {
                    readerModel.onEvent(event)
                    scope.launch {
                        delay(100) // дождаться update в стейте
                        val index = readerModel.findGlobalIndexForBookmark(
                            chapterIndex = event.chapterIndex,
                            offset = event.offset.toInt()
                        )
                        listState.animateScrollToItem(index)
                    }
                }
                else -> readerModel.onEvent(event)
            }
        }



        ReaderLayout(
            text = text,
            listState = listState,
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
            isLoading = isLoading,
            showMenu = showMenu,
            menuVisibility = menuVisibility,
            openShareApp = openShareApp,
            openWebBrowser = openWebBrowser,
            openTranslator = openTranslator,
            openDictionary = openDictionary,
            selectedTranslator = selectedTranslator,

            // 👇 передача для закладок
            onEvent = onEventHandler,
            currentChapterIndex = chapterOrdinalIndex,
            currentOffset = checkpoint.offset.toLong(),
            bookId = book.id.toLong(),
            highlightedText = highlightedText
        )

        ReaderPerceptionExpander(
            perceptionExpander = perceptionExpander,
            perceptionExpanderPadding = perceptionExpanderPadding,
            perceptionExpanderThickness = perceptionExpanderThickness,
            perceptionExpanderColor = fontColor
        )

        if (isLoading) {
            ReaderLoadingPlaceholder()
        }
    }
}
