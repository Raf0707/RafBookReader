/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.reader

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import raf.console.chitalka.domain.reader.ReaderText.Chapter
import raf.console.chitalka.domain.reader.ReaderTextAlignment
import raf.console.chitalka.presentation.core.components.common.StyledText

@Composable
fun LazyItemScope.ReaderLayoutTextChapter(
    chapter: Chapter,
    chapterTitleAlignment: ReaderTextAlignment,
    fontColor: Color,
    sidePadding: Dp,
    highlightedReading: Boolean,
    highlightedReadingThickness: FontWeight
) {
    Column(
        Modifier
            .animateItem(
                fadeInSpec = null,
                fadeOutSpec = null
            )
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(22.dp))

        StyledText(
            text = buildAnnotatedString { append(chapter.title) },
            modifier = Modifier
                .padding(horizontal = sidePadding)
                .fillMaxWidth(),
            style = (if (!chapter.nested) MaterialTheme.typography.headlineMedium
            else MaterialTheme.typography.headlineSmall)
                .copy(
                    color = fontColor,
                    textAlign = chapterTitleAlignment.textAlignment
                ),
            highlightText = highlightedReading,
            highlightThickness = highlightedReadingThickness
        )

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = fontColor.copy(0.4f))
        Spacer(modifier = Modifier.height(16.dp))
    }
}