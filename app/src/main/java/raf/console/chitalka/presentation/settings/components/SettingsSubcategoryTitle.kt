/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import raf.console.chitalka.presentation.core.components.common.StyledText

@Composable
fun SettingsSubcategoryTitle(
    modifier: Modifier = Modifier,
    title: String,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    padding: Dp = 18.dp
) {
    StyledText(
        text = title,
        modifier = modifier.padding(horizontal = padding),
        style = TextStyle(
            lineBreak = LineBreak.Heading,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            color = color
        )
    )
}