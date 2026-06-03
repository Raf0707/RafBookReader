/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.reader

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import raf.console.chitalka.domain.reader.FontWithName
import raf.console.chitalka.domain.reader.ReaderFontThickness
import raf.console.chitalka.domain.reader.ReaderText
import raf.console.chitalka.domain.reader.ReaderTextAlignment
import raf.console.chitalka.domain.util.HorizontalAlignment
import raf.console.chitalka.ui.reader.ReaderEvent

@Composable
fun LazyItemScope.ReaderLayoutText(
    activity: ComponentActivity,
    showMenu: Boolean,
    entry: ReaderText,
    imagesCornersRoundness: Dp,
    imagesAlignment: HorizontalAlignment,
    imagesWidth: Float,
    imagesColorEffects: ColorFilter?,
    fontFamily: FontWithName,
    fontColor: Color,
    lineHeight: TextUnit,
    fontThickness: ReaderFontThickness,
    fontStyle: FontStyle,
    chapterTitleAlignment: ReaderTextAlignment,
    textAlignment: ReaderTextAlignment,
    horizontalAlignment: Alignment.Horizontal,
    fontSize: TextUnit,
    letterSpacing: TextUnit,
    sidePadding: Dp,
    paragraphIndentation: TextUnit,
    fullscreenMode: Boolean,
    doubleClickTranslation: Boolean,
    highlightedReading: Boolean,
    highlightedReadingThickness: FontWeight,
    toolbarHidden: Boolean,
    highlightedText: String?,
    isBookmarked: Boolean = false, // добавили этот параметр с дефолтным значением
    openTranslator: (ReaderEvent.OnOpenTranslator) -> Unit,
    menuVisibility: (ReaderEvent.OnMenuVisibility) -> Unit,
    modifier: Modifier = Modifier
) {
    when (entry) {
        /*is ReaderText.Image -> {
            ReaderLayoutTextImage(
                entry = entry,
                sidePadding = sidePadding,
                imagesCornersRoundness = imagesCornersRoundness,
                imagesAlignment = imagesAlignment,
                imagesWidth = imagesWidth,
                imagesColorEffects = imagesColorEffects
            )
        }*/

        // 🔽 Новый блок для PDF-страниц и картинок
        is ReaderText.Image -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth() // ✅ заполняем всю ширину экрана
                    .padding(horizontal = sidePadding)
                    .clip(RoundedCornerShape(imagesCornersRoundness))
                    .border(
                        width = 1.dp,
                        color = Color.Gray.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(imagesCornersRoundness)
                    )
                    .background(Color.Transparent)
                    .padding(4.dp),
                contentAlignment = when (imagesAlignment) {
                    HorizontalAlignment.CENTER -> Alignment.Center
                    HorizontalAlignment.START -> Alignment.CenterStart
                    HorizontalAlignment.END -> Alignment.CenterEnd
                }
            ) {
                androidx.compose.foundation.Image(
                    bitmap = entry.imageBitmap,
                    contentDescription = null,
                    colorFilter = imagesColorEffects,
                    modifier = Modifier.fillMaxWidth(), // ✅ РАСТЯГИВАЕТ картинку
                    contentScale = ContentScale.FillWidth // ✅ РАСТЯГИВАЕМ по ширине
                )
            }
        }


        is ReaderText.Separator -> {
            ReaderLayoutTextSeparator(
                sidePadding = sidePadding,
                fontColor = fontColor
            )
        }

        is ReaderText.Chapter -> {
            ReaderLayoutTextChapter(
                chapter = entry,
                chapterTitleAlignment = chapterTitleAlignment,
                fontColor = fontColor,
                sidePadding = sidePadding,
                highlightedReading = highlightedReading,
                highlightedReadingThickness = highlightedReadingThickness
            )
        }

        is ReaderText.Text -> {
            ReaderLayoutTextParagraph(
                paragraph = entry,
                activity = activity,
                showMenu = showMenu,
                fontFamily = fontFamily,
                fontColor = fontColor,
                lineHeight = lineHeight,
                fontThickness = fontThickness,
                fontStyle = fontStyle,
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
                highlightedText = highlightedText,
                isBookmarked = isBookmarked, // ← передача значения здесь
            )
        }




        is ReaderText.Math -> {
            ReaderLayoutMath(
                latex = entry.latex,
                fontColor = fontColor,
                fontSize = fontSize,
                sidePadding = sidePadding
            )
        }
    }
}
