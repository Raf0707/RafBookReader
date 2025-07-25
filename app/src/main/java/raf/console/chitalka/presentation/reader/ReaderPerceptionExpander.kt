/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.reader

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp

@Composable
fun ReaderPerceptionExpander(
    perceptionExpander: Boolean,
    perceptionExpanderPadding: Dp,
    perceptionExpanderThickness: Dp,
    perceptionExpanderColor: Color
) {
    if (perceptionExpander) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = perceptionExpanderPadding.coerceAtMost(
                        with(LocalDensity.current) {
                            LocalWindowInfo.current.containerSize.width.toDp()
                        }.run {
                            this / 2f - (this * 0.1f)
                        }
                    )
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(2) {
                VerticalDivider(
                    color = perceptionExpanderColor,
                    thickness = perceptionExpanderThickness
                )
            }
        }
    }
}