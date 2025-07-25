/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.book_info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.presentation.core.components.common.StyledText
import raf.console.chitalka.presentation.core.util.calculateProgress

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BookInfoLayoutInfoProgress(
    book: Book
) {
    val progress = remember(book.progress) {
        "${book.progress.calculateProgress(1)}%"
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LinearWavyProgressIndicator(
            progress = { book.progress.coerceIn(0f, 1f) },
            trackColor = MaterialTheme.colorScheme.secondaryContainer.copy(0.7f),
            modifier = Modifier.weight(1f),
            amplitude = { 0.5f },
            wavelength = 80.dp,
            waveSpeed = 15.dp
        )
        StyledText(
            text = progress,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            ),
        )
    }
}