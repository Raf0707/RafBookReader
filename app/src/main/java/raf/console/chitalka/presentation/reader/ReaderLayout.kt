/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.reader

import android.os.Build
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import raf.console.chitalka.R
import raf.console.chitalka.domain.reader.FontWithName
import raf.console.chitalka.domain.reader.ReaderFontThickness
import raf.console.chitalka.domain.reader.ReaderHorizontalGesture
import raf.console.chitalka.domain.reader.ReaderText
import raf.console.chitalka.domain.reader.ReaderTextAlignment
import raf.console.chitalka.domain.util.HorizontalAlignment
import raf.console.chitalka.presentation.core.components.common.AnimatedVisibility
import raf.console.chitalka.presentation.core.components.common.LazyColumnWithScrollbar
//import raf.console.chitalka.presentation.core.components.common.SafeSelectionContainer
import raf.console.chitalka.presentation.core.components.common.SelectionContainer
import raf.console.chitalka.presentation.core.components.common.SpacedItem
import raf.console.chitalka.presentation.core.util.LocalActivity
import raf.console.chitalka.presentation.core.util.noRippleClickable
import raf.console.chitalka.presentation.core.util.showToast
import raf.console.chitalka.presentation.reader.translator.TranslatorApp
import raf.console.chitalka.ui.reader.ReaderEvent

@Composable
fun ReaderLayout(
    text: List<ReaderText>,
    listState: LazyListState,
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
    isLoading: Boolean,
    showMenu: Boolean,
    menuVisibility: (ReaderEvent.OnMenuVisibility) -> Unit,
    openShareApp: (ReaderEvent.OnOpenShareApp) -> Unit,
    openWebBrowser: (ReaderEvent.OnOpenWebBrowser) -> Unit,
    openTranslator: (ReaderEvent.OnOpenTranslator) -> Unit,
    openDictionary: (ReaderEvent.OnOpenDictionary) -> Unit,
    selectedTranslator: TranslatorApp,

    // 🆕 новые аргументы:
    bookId: Long,
    currentChapterIndex: Int,
    currentOffset: Long,
    onEvent: (ReaderEvent) -> Unit,
    highlightedText: String?,

    ) {

    val activity = LocalActivity.current

    Column(
        Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .then(
                if (!isLoading && showMenu.not()) {
                    Modifier.noRippleClickable {
                        menuVisibility(
                            ReaderEvent.OnMenuVisibility(
                                show = !showMenu,
                                fullscreenMode = fullscreenMode,
                                saveCheckpoint = true,
                                activity = activity
                            )
                        )
                    }
                } else Modifier
            )
            .padding(contentPadding)
            .padding(vertical = verticalPadding)
            .readerHorizontalGesture(
                listState = listState,
                horizontalGesture = horizontalGesture,
                horizontalGestureScroll = horizontalGestureScroll,
                horizontalGestureSensitivity = horizontalGestureSensitivity,
                horizontalGestureAlphaAnim = horizontalGestureAlphaAnim,
                horizontalGesturePullAnim = horizontalGesturePullAnim,
                isLoading = isLoading
            )
    ) {

        SelectionContainer(
            selectedTranslator = selectedTranslator,
            bookId = bookId,
            getCurrentChapterIndex = { currentChapterIndex },
            getCurrentOffset = { currentOffset },
            onEvent = onEvent,

            onCopyRequested = {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                    activity.getString(R.string.copied).showToast(context = activity)
                }
            },
            onSoundRequested = {},
            listState = listState,
            onShareRequested = {
                openShareApp(ReaderEvent.OnOpenShareApp(it, activity))
            },
            onWebSearchRequested = {
                openWebBrowser(ReaderEvent.OnOpenWebBrowser(it, activity))
            },
            onTranslateRequested = {
                openTranslator(ReaderEvent.OnOpenTranslator(it, false, activity))
            },
            onDictionaryRequested = {
                openDictionary(ReaderEvent.OnOpenDictionary(it, activity))
            },

            content = { toolbarHidden ->
                LazyColumnWithScrollbar(
                    state = listState,
                    enableScrollbar = false,
                    parentModifier = Modifier.weight(1f),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        top = (WindowInsets.displayCutout.asPaddingValues().calculateTopPadding() + paragraphHeight).coerceAtLeast(18.dp),
                        bottom = (WindowInsets.displayCutout.asPaddingValues().calculateBottomPadding() + paragraphHeight).coerceAtLeast(18.dp),
                    )
                ) {
                    itemsIndexed(text, key = { index, _ -> index }) { index, entry ->
                        if (!images && entry is ReaderText.Image) return@itemsIndexed

                        SpacedItem(index = index, spacing = paragraphHeight) {
                            ReaderLayoutText(
                                activity = activity,
                                showMenu = showMenu,
                                entry = entry,
                                imagesCornersRoundness = imagesCornersRoundness,
                                imagesAlignment = imagesAlignment,
                                imagesWidth = imagesWidth,
                                imagesColorEffects = imagesColorEffects,
                                fontFamily = fontFamily,
                                fontColor = fontColor,
                                lineHeight = lineHeight,
                                fontThickness = fontThickness,
                                fontStyle = fontStyle,
                                chapterTitleAlignment = chapterTitleAlignment,
                                textAlignment = textAlignment,
                                horizontalAlignment = horizontalAlignment,
                                fontSize = fontSize,
                                letterSpacing = letterSpacing,
                                sidePadding = sidePadding,
                                paragraphIndentation = paragraphIndentation,
                                fullscreenMode = fullscreenMode,
                                doubleClickTranslation = doubleClickTranslation,
                                highlightedReading = highlightedReading,
                                highlightedReadingThickness = highlightedReadingThickness,
                                toolbarHidden = toolbarHidden,
                                openTranslator = openTranslator,
                                menuVisibility = menuVisibility,
                                highlightedText = highlightedText // 👈 сюда добавляем новый аргумент
                            )
                        }
                    }
                }
            },
            text = text
        )


        AnimatedVisibility(
            visible = !showMenu && progressBar,
            enter = slideInVertically { it } + expandVertically(),
            exit = slideOutVertically { it } + shrinkVertically()
        ) {
            ReaderProgressBar(
                progress = progress,
                progressBarPadding = progressBarPadding,
                progressBarAlignment = progressBarAlignment,
                progressBarFontSize = progressBarFontSize,
                fontColor = fontColor,
                sidePadding = sidePadding
            )
        }
    }
}