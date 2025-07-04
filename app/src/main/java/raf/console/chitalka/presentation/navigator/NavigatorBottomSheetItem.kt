/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.navigator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import raf.console.chitalka.domain.util.Position
import raf.console.chitalka.presentation.core.components.common.StyledText

@Composable
fun NavigatorBottomSheetItem(
    title: String,
    primary: Boolean,
    position: Position,
    onClick: () -> Unit
) {
    val largeShape = MaterialTheme.shapes.large
    val shape = remember(position) {
        when (position) {
            Position.TOP -> largeShape.copy(
                bottomStart = CornerSize(3.dp),
                bottomEnd = CornerSize(3.dp)
            )

            Position.CENTER -> RoundedCornerShape(3.dp)

            Position.SOLO -> largeShape

            Position.BOTTOM -> largeShape.copy(
                topStart = CornerSize(3.dp),
                topEnd = CornerSize(3.dp)
            )
        }
    }
    val paddingValues = remember(position) {
        when (position) {
            Position.TOP -> PaddingValues(bottom = 1.dp)
            Position.CENTER -> PaddingValues(vertical = 1.dp)
            Position.SOLO -> PaddingValues(0.dp)
            Position.BOTTOM -> PaddingValues(top = 1.dp)
        }
    }

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .clip(shape = shape)
            .background(
                if (primary) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surfaceContainer
            )
            .clickable {
                onClick()
            }
            .padding(horizontal = 18.dp, vertical = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        StyledText(
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(
                color = if (primary) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurface
            )
        )
    }
}