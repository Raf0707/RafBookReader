/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.ui.reader

import android.content.pm.ActivityInfo
import android.os.Parcelable
import android.view.WindowManager
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.systemBarsIgnoringVisibility
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.parcelize.Parcelize
import raf.console.chitalka.domain.navigator.Screen
import raf.console.chitalka.domain.reader.ReaderColorEffects
import raf.console.chitalka.domain.reader.ReaderProgressCount
import raf.console.chitalka.domain.reader.ReaderTextAlignment
import raf.console.chitalka.presentation.core.constants.provideFonts
import raf.console.chitalka.presentation.core.util.LocalActivity
import raf.console.chitalka.presentation.core.util.calculateProgress
import raf.console.chitalka.presentation.core.util.setBrightness
import raf.console.chitalka.presentation.navigator.LocalNavigator
import raf.console.chitalka.presentation.reader.ReaderContent
import raf.console.chitalka.ui.book_info.BookInfoScreen
import raf.console.chitalka.ui.main.MainModel
import raf.console.chitalka.ui.settings.SettingsModel
import kotlin.math.roundToInt

@Parcelize
data class ReaderScreen(val bookId: Int) : Screen, Parcelable {

    companion object {
        const val CHAPTERS_DRAWER = "chapters_drawer"
        const val SETTINGS_BOTTOM_SHEET = "settings_bottom_sheet"
        const val NOTES_BOOKMARKS_DRAWER = "notes_bookmarks_drawer"
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navigator = LocalNavigator.current
        val screenModel = hiltViewModel<ReaderModel>()
        val mainModel = hiltViewModel<MainModel>()
        val settingsModel = hiltViewModel<SettingsModel>()

        val state = screenModel.state.collectAsStateWithLifecycle()
        val mainState = mainModel.state.collectAsStateWithLifecycle()
        val settingsState = settingsModel.state.collectAsStateWithLifecycle()

        val activity = LocalActivity.current
        val density = LocalDensity.current
        val listState = rememberSaveable(
            state.value.listState,
            saver = LazyListState.Saver
        ) {
            state.value.listState
        }

        val nestedScrollConnection = remember {
            derivedStateOf {
                object : NestedScrollConnection {
                    override fun onPostScroll(
                        consumed: Offset,
                        available: Offset,
                        source: NestedScrollSource
                    ): Offset {
                        consumed.y.let { velocity ->
                            if (velocity in -70f..70f) return@let
                            if (!state.value.showMenu) return@let
                            if (state.value.lockMenu) return@let
                            if (!mainState.value.hideBarsOnFastScroll) return@let

                            screenModel.onEvent(
                                ReaderEvent.OnMenuVisibility(
                                    show = false,
                                    fullscreenMode = mainState.value.fullscreen,
                                    saveCheckpoint = false,
                                    activity = activity
                                )
                            )
                        }
                        return super.onPostScroll(consumed, available, source)
                    }
                }
            }
        }

        val fontFamily = remember(mainState.value.fontFamily) {
            provideFonts().run {
                find {
                    it.id == mainState.value.fontFamily
                } ?: get(0)
            }
        }
        val backgroundColor = animateColorAsState(
            targetValue = settingsState.value.selectedColorPreset.backgroundColor
        )
        val fontColor = animateColorAsState(
            targetValue = settingsState.value.selectedColorPreset.fontColor
        )
        val lineHeight = remember(
            mainState.value.fontSize,
            mainState.value.lineHeight
        ) {
            (mainState.value.fontSize + mainState.value.lineHeight).sp
        }
        val letterSpacing = remember(mainState.value.letterSpacing) {
            (mainState.value.letterSpacing / 100f).em
        }
        val sidePadding = remember(mainState.value.sidePadding) {
            (mainState.value.sidePadding * 3).dp
        }
        val verticalPadding = remember(mainState.value.verticalPadding) {
            (mainState.value.verticalPadding * 4.5f).dp
        }
        val paragraphHeight = remember(
            mainState.value.paragraphHeight,
            mainState.value.lineHeight
        ) {
            ((mainState.value.paragraphHeight * 3).dp).coerceAtLeast(
                with(density) { mainState.value.lineHeight.sp.toDp().value * 0.5f }.dp
            )
        }
        val fontStyle = remember(mainState.value.isItalic) {
            when (mainState.value.isItalic) {
                true -> FontStyle.Italic
                false -> FontStyle.Normal
            }
        }
        val paragraphIndentation = remember(
            mainState.value.paragraphIndentation,
            mainState.value.textAlignment
        ) {
            if (
                mainState.value.textAlignment == ReaderTextAlignment.CENTER ||
                mainState.value.textAlignment == ReaderTextAlignment.END
            ) {
                return@remember 0.sp
            }
            (mainState.value.paragraphIndentation * 6).sp
        }
        val perceptionExpanderPadding = remember(
            sidePadding,
            mainState.value.perceptionExpanderPadding
        ) {
            sidePadding + (mainState.value.perceptionExpanderPadding * 8).dp
        }
        val perceptionExpanderThickness = remember(
            mainState.value.perceptionExpanderThickness
        ) {
            (mainState.value.perceptionExpanderThickness * 0.25f).dp
        }
        val horizontalGestureSensitivity = remember(mainState.value.horizontalGestureSensitivity) {
            (36f + mainState.value.horizontalGestureSensitivity * (4f - 36f)).dp
        }
        val highlightedReadingThickness = remember(mainState.value.highlightedReadingThickness) {
            when (mainState.value.highlightedReadingThickness) {
                2 -> FontWeight.SemiBold
                3 -> FontWeight.Bold
                else -> FontWeight.Medium
            }
        }
        val horizontalAlignment = remember(mainState.value.textAlignment) {
            when (mainState.value.textAlignment) {
                ReaderTextAlignment.START, ReaderTextAlignment.JUSTIFY -> Alignment.Start
                ReaderTextAlignment.CENTER -> Alignment.CenterHorizontally
                ReaderTextAlignment.END -> Alignment.End
            }
        }
        val imagesWidth = remember(mainState.value.imagesWidth) {
            mainState.value.imagesWidth.coerceAtLeast(0.01f)
        }
        val imagesCornersRoundness = remember(
            mainState.value.imagesCornersRoundness,
            mainState.value.imagesWidth
        ) {
            (mainState.value.imagesCornersRoundness * 3 * imagesWidth).dp
        }
        val imagesColorEffects = remember(
            mainState.value.imagesColorEffects,
            fontColor.value,
            backgroundColor.value
        ) {
            when (mainState.value.imagesColorEffects) {
                ReaderColorEffects.OFF -> null

                ReaderColorEffects.GRAYSCALE -> ColorFilter.colorMatrix(
                    ColorMatrix().apply { setToSaturation(0f) }
                )

                ReaderColorEffects.FONT -> ColorFilter.tint(
                    color = fontColor.value,
                    blendMode = BlendMode.Color
                )

                ReaderColorEffects.BACKGROUND -> ColorFilter.tint(
                    color = backgroundColor.value,
                    blendMode = BlendMode.Color
                )
            }
        }
        val progressBarPadding = remember(mainState.value.progressBarPadding) {
            (mainState.value.progressBarPadding * 3).dp
        }
        val progressBarFontSize = remember(mainState.value.progressBarFontSize) {
            (mainState.value.progressBarFontSize * 2).sp
        }

        val layoutDirection = LocalLayoutDirection.current
        val cutoutInsets = WindowInsets.displayCutout
        val systemBarsInsets = WindowInsets.systemBarsIgnoringVisibility

        val cutoutInsetsPadding = remember(mainState.value.cutoutPadding) {
            derivedStateOf {
                cutoutInsets.asPaddingValues(density = density).run {
                    if (mainState.value.cutoutPadding) PaddingValues(
                        top = calculateTopPadding(),
                        start = calculateStartPadding(layoutDirection),
                        end = calculateEndPadding(layoutDirection),
                        bottom = calculateBottomPadding()
                    ) else PaddingValues(0.dp)
                }
            }
        }
        val systemBarsInsetsPadding = remember(mainState.value.fullscreen) {
            derivedStateOf {
                systemBarsInsets.asPaddingValues(density = density).run {
                    if (!mainState.value.fullscreen) PaddingValues(
                        top = calculateTopPadding(),
                        start = calculateStartPadding(layoutDirection),
                        end = calculateEndPadding(layoutDirection),
                        bottom = calculateBottomPadding()
                    ) else PaddingValues(0.dp)
                }
            }
        }
        val contentPadding = remember(
            cutoutInsetsPadding.value,
            systemBarsInsetsPadding.value
        ) {
            PaddingValues(
                top = systemBarsInsetsPadding.value.calculateTopPadding().run {
                    if (equals(0.dp)) return@run cutoutInsetsPadding.value
                        .calculateTopPadding()
                    this
                },
                start = systemBarsInsetsPadding.value.calculateStartPadding(layoutDirection).run {
                    if (equals(0.dp)) return@run cutoutInsetsPadding.value
                        .calculateStartPadding(layoutDirection)
                    this
                },
                end = systemBarsInsetsPadding.value.calculateEndPadding(layoutDirection).run {
                    if (equals(0.dp)) return@run cutoutInsetsPadding.value
                        .calculateEndPadding(layoutDirection)
                    this
                },
                bottom = systemBarsInsetsPadding.value.calculateBottomPadding().run {
                    if (equals(0.dp)) return@run cutoutInsetsPadding.value
                        .calculateBottomPadding()
                    this
                }
            )
        }
        val bottomBarPadding = remember(mainState.value.bottomBarPadding) {
            (mainState.value.bottomBarPadding * 4f).dp
        }

        val bookProgress = remember(
            state.value.book.progress,
            state.value.text,
            mainState.value.progressCount
        ) {
            when (mainState.value.progressCount) {
                ReaderProgressCount.PERCENTAGE -> {
                    "${state.value.book.progress.calculateProgress(2)}%"
                }

                ReaderProgressCount.QUANTITY -> {
                    val index =
                        (state.value.book.progress * state.value.text.lastIndex + 1).roundToInt()
                    "$index / ${state.value.text.size}"
                }
            }
        }
        val chapterProgress = remember(
            state.value.text,
            state.value.book.progress,
            state.value.currentChapter,
            state.value.currentChapterProgress,
            mainState.value.progressCount
        ) {
            if (state.value.currentChapter == null) return@remember ""
            when (mainState.value.progressCount) {
                ReaderProgressCount.PERCENTAGE -> {
                    " (${state.value.currentChapterProgress.calculateProgress(2)}%)"
                }

                ReaderProgressCount.QUANTITY -> {
                    val (index, length) = screenModel.findChapterIndexAndLength(
                        (state.value.book.progress * state.value.text.lastIndex).roundToInt()
                    ).apply { if (first == -1 && second == -1) return@remember "" }
                    " (${index} / ${length})"
                }
            }
        }
        val progress = remember(bookProgress, chapterProgress) {
            "${bookProgress}${chapterProgress}"
        }

        LaunchedEffect(bookId) {
            screenModel.startObservingNotes(bookId.toLong())
            //screenModel.startObservingAllNotes()
        }

        LaunchedEffect(Unit) {
            screenModel.init(
                bookId = bookId,
                fullscreenMode = mainState.value.fullscreen,
                activity = activity,
                navigateBack = {
                    navigator.pop()
                }
            )
        }
        LaunchedEffect(mainState.value.fullscreen) {
            screenModel.onEvent(
                ReaderEvent.OnMenuVisibility(
                    show = state.value.showMenu,
                    fullscreenMode = mainState.value.fullscreen,
                    saveCheckpoint = false,
                    activity = activity
                )
            )
        }
        LaunchedEffect(listState) {
            screenModel.updateProgress(listState)
        }

        DisposableEffect(mainState.value.screenOrientation) {
            activity.requestedOrientation = mainState.value.screenOrientation.code
            onDispose {
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }
        DisposableEffect(
            mainState.value.screenBrightness,
            mainState.value.customScreenBrightness
        ) {
            when (mainState.value.customScreenBrightness) {
                true -> activity.setBrightness(brightness = mainState.value.screenBrightness)
                false -> activity.setBrightness(brightness = null)
            }

            onDispose {
                activity.setBrightness(brightness = null)
            }
        }
        DisposableEffect(mainState.value.keepScreenOn) {
            when (mainState.value.keepScreenOn) {
                true -> activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                false -> activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
            onDispose {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                screenModel.resetScreen()
                WindowCompat.getInsetsController(
                    activity.window,
                    activity.window.decorView
                ).show(WindowInsetsCompat.Type.systemBars())
            }
        }

        ReaderContent(
            book = state.value.book,
            text = state.value.text,
            bottomSheet = state.value.bottomSheet,
            drawer = state.value.drawer,
            listState = listState,
            currentChapter = state.value.currentChapter,
            nestedScrollConnection = nestedScrollConnection.value,
            fastColorPresetChange = mainState.value.fastColorPresetChange,
            perceptionExpander = mainState.value.perceptionExpander,
            perceptionExpanderPadding = perceptionExpanderPadding,
            perceptionExpanderThickness = perceptionExpanderThickness,
            currentChapterProgress = state.value.currentChapterProgress,
            isLoading = state.value.isLoading,
            errorMessage = state.value.errorMessage,
            checkpoint = state.value.checkpoint,
            showMenu = state.value.showMenu,
            lockMenu = state.value.lockMenu,
            contentPadding = contentPadding,
            verticalPadding = verticalPadding,
            horizontalGesture = mainState.value.horizontalGesture,
            horizontalGestureScroll = mainState.value.horizontalGestureScroll,
            horizontalGestureSensitivity = horizontalGestureSensitivity,
            horizontalGestureAlphaAnim = mainState.value.horizontalGestureAlphaAnim,
            horizontalGesturePullAnim = mainState.value.horizontalGesturePullAnim,
            highlightedReading = mainState.value.highlightedReading,
            highlightedReadingThickness = highlightedReadingThickness,
            progress = progress,
            progressBar = mainState.value.progressBar,
            progressBarPadding = progressBarPadding,
            progressBarAlignment = mainState.value.progressBarAlignment,
            progressBarFontSize = progressBarFontSize,
            paragraphHeight = paragraphHeight,
            sidePadding = sidePadding,
            bottomBarPadding = bottomBarPadding,
            backgroundColor = backgroundColor.value,
            fontColor = fontColor.value,
            images = mainState.value.images,
            imagesCornersRoundness = imagesCornersRoundness,
            imagesAlignment = mainState.value.imagesAlignment,
            imagesWidth = imagesWidth,
            imagesColorEffects = imagesColorEffects,
            fontFamily = fontFamily,
            lineHeight = lineHeight,
            fontThickness = mainState.value.fontThickness,
            fontStyle = fontStyle,
            chapterTitleAlignment = mainState.value.chapterTitleAlignment,
            textAlignment = mainState.value.textAlignment,
            horizontalAlignment = horizontalAlignment,
            fontSize = mainState.value.fontSize.sp,
            letterSpacing = letterSpacing,
            paragraphIndentation = paragraphIndentation,
            doubleClickTranslation = mainState.value.doubleClickTranslation,
            fullscreenMode = mainState.value.fullscreen,
            selectPreviousPreset = settingsModel::onEvent,
            selectNextPreset = settingsModel::onEvent,
            leave = screenModel::onEvent,
            restoreCheckpoint = screenModel::onEvent,
            scroll = screenModel::onEvent,
            changeProgress = screenModel::onEvent,
            menuVisibility = screenModel::onEvent,
            openShareApp = screenModel::onEvent,
            openWebBrowser = screenModel::onEvent,
            openTranslator = screenModel::onEvent,
            openDictionary = screenModel::onEvent,
            scrollToChapter = screenModel::onEvent,
            showSettingsBottomSheet = screenModel::onEvent,
            dismissBottomSheet = screenModel::onEvent,
            showChaptersDrawer = screenModel::onEvent,
            dismissDrawer = screenModel::onEvent,
            navigateBack = {
                navigator.pop()
            },
            navigateToBookInfo = { changePath ->
                if (changePath) BookInfoScreen.changePathChannel.trySend(true)
                navigator.push(
                    BookInfoScreen(
                        bookId = bookId,
                    ),
                    popping = true,
                    saveInBackStack = false
                )
            },
            onShowNotesDrawer = {
                screenModel.onEvent(ReaderEvent.OnShowNotesBookmarksDrawer(state.value.book.id.toLong()))
            },
            onStartTTS = {
                screenModel.onEvent(ReaderEvent.OnStartTextToSpeech(context))
            },
            selectedTranslator = mainState.value.selectedTranslator,
            onEvent = screenModel::onEvent,
            bookmarks = state.value.bookmarks,
            notes = state.value.notes,
            highlightedText = state.value.highlightedText,
            readerModel = screenModel

            )


    }
}