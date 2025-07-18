/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.ui.main

import androidx.compose.runtime.Immutable
import raf.console.chitalka.presentation.reader.translator.TranslatorApp

@Immutable
sealed class MainEvent {
    data class OnChangeLanguage(val value: String) : MainEvent()
    data class OnChangeTheme(val value: String) : MainEvent()
    data class OnChangeDarkTheme(val value: String) : MainEvent()
    data class OnChangePureDark(val value: String) : MainEvent()
    data class OnChangeThemeContrast(val value: String) : MainEvent()
    data class OnChangeFontFamily(val value: String) : MainEvent()
    data class OnChangeFontStyle(val value: Boolean) : MainEvent()
    data class OnChangeFontSize(val value: Int) : MainEvent()
    data class OnChangeLineHeight(val value: Int) : MainEvent()
    data class OnChangeParagraphHeight(val value: Int) : MainEvent()
    data class OnChangeParagraphIndentation(val value: Int) : MainEvent()
    data class OnChangeShowStartScreen(val value: Boolean) : MainEvent()
    data class OnChangeSidePadding(val value: Int) : MainEvent()
    data class OnChangeDoubleClickTranslation(val value: Boolean) : MainEvent()
    data class OnChangeFastColorPresetChange(val value: Boolean) : MainEvent()
    data class OnChangeBrowseLayout(val value: String) : MainEvent()
    data class OnChangeBrowseAutoGridSize(val value: Boolean) : MainEvent()
    data class OnChangeBrowseGridSize(val value: Int) : MainEvent()
    data class OnChangeBrowseSortOrder(val value: String) : MainEvent()
    data class OnChangeBrowseSortOrderDescending(val value: Boolean) : MainEvent()
    data class OnChangeBrowseIncludedFilterItem(val value: String) : MainEvent()
    data class OnChangeTextAlignment(val value: String) : MainEvent()
    data class OnChangeDoublePressExit(val value: Boolean) : MainEvent()
    data class OnChangeLetterSpacing(val value: Int) : MainEvent()
    data class OnChangeAbsoluteDark(val value: Boolean) : MainEvent()
    data class OnChangeCutoutPadding(val value: Boolean) : MainEvent()
    data class OnChangeFullscreen(val value: Boolean) : MainEvent()
    data class OnChangeKeepScreenOn(val value: Boolean) : MainEvent()
    data class OnChangeVerticalPadding(val value: Int) : MainEvent()
    data class OnChangeHideBarsOnFastScroll(val value: Boolean) : MainEvent()
    data class OnChangePerceptionExpander(val value: Boolean) : MainEvent()
    data class OnChangePerceptionExpanderPadding(val value: Int) : MainEvent()
    data class OnChangePerceptionExpanderThickness(val value: Int) : MainEvent()
    data class OnChangeScreenOrientation(val value: String) : MainEvent()
    data class OnChangeCustomScreenBrightness(val value: Boolean) : MainEvent()
    data class OnChangeScreenBrightness(val value: Float) : MainEvent()
    data class OnChangeHorizontalGesture(val value: String) : MainEvent()
    data class OnChangeHorizontalGestureScroll(val value: Float) : MainEvent()
    data class OnChangeHorizontalGestureSensitivity(val value: Float) : MainEvent()
    data class OnChangeBottomBarPadding(val value: Int) : MainEvent()
    data class OnChangeHighlightedReading(val value: Boolean) : MainEvent()
    data class OnChangeHighlightedReadingThickness(val value: Int) : MainEvent()
    data class OnChangeChapterTitleAlignment(val value: String) : MainEvent()
    data class OnChangeImages(val value: Boolean) : MainEvent()
    data class OnChangeImagesCornersRoundness(val value: Int) : MainEvent()
    data class OnChangeImagesAlignment(val value: String) : MainEvent()
    data class OnChangeImagesWidth(val value: Float) : MainEvent()
    data class OnChangeImagesColorEffects(val value: String) : MainEvent()
    data class OnChangeProgressBar(val value: Boolean) : MainEvent()
    data class OnChangeProgressBarPadding(val value: Int) : MainEvent()
    data class OnChangeProgressBarAlignment(val value: String) : MainEvent()
    data class OnChangeProgressBarFontSize(val value: Int) : MainEvent()
    data class OnChangeBrowsePinnedPaths(val value: String) : MainEvent()
    data class OnChangeFontThickness(val value: String) : MainEvent()
    data class OnChangeProgressCount(val value: String) : MainEvent()
    data class OnChangeHorizontalGestureAlphaAnim(val value: Boolean) : MainEvent()
    data class OnChangeHorizontalGesturePullAnim(val value: Boolean) : MainEvent()
    data class OnChangeRenderMath(val value: Boolean) : MainEvent()
    data class OnSelectTranslator(val value: Boolean) : MainEvent()
    data class OnShowNotesBookmarksDrawer(val bookId: Int) : MainEvent()

}