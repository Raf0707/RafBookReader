/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.reader

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LazyItemScope.ReaderLayoutTextSeparator(
    sidePadding: Dp,
    fontColor: Color
) {
    HorizontalDivider(
        thickness = 3.dp,
        modifier = Modifier
            .animateItem(
                fadeInSpec = null,
                fadeOutSpec = null
            )
            .padding(horizontal = sidePadding)
            .clip(CircleShape),
        color = fontColor.copy(0.3f)
    )
}