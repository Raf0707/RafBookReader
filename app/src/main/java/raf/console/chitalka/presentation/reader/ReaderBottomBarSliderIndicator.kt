/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.reader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ReaderBottomBarSliderIndicator(progress: Float) {
    Row(Modifier.fillMaxWidth()) {
        Spacer(
            modifier = Modifier.fillMaxWidth(progress)
        )
        Box(
            Modifier
                .width(4.dp)
                .height(16.dp)
                .clip(RoundedCornerShape(0.5.dp))
                .background(MaterialTheme.colorScheme.onPrimary.copy(0.6f))
        )
    }
}