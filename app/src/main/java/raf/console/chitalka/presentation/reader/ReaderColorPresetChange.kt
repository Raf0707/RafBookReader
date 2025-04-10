/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf0707 for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.reader

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import raf.console.chitalka.ui.settings.SettingsEvent

@Composable
fun Modifier.readerColorPresetChange(
    colorPresetChangeEnabled: Boolean,
    isLoading: Boolean,
    selectPreviousPreset: (SettingsEvent.OnSelectPreviousPreset) -> Unit,
    selectNextPreset: (SettingsEvent.OnSelectNextPreset) -> Unit
): Modifier {
    val context = LocalContext.current
    val offset = remember { mutableFloatStateOf(0f) }
    return this.then(
        if (colorPresetChangeEnabled && !isLoading) {
            Modifier.pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = { offset.floatValue = 0f },
                    onDragCancel = { offset.floatValue = 0f },
                    onDragEnd = {
                        when {
                            offset.floatValue > 200 -> {
                                selectPreviousPreset(
                                    SettingsEvent.OnSelectPreviousPreset(
                                        context = context
                                    )
                                )
                            }

                            offset.floatValue < -200 -> {
                                selectNextPreset(
                                    SettingsEvent.OnSelectNextPreset(
                                        context = context
                                    )
                                )
                            }
                        }
                    }
                ) { _, dragAmount ->
                    offset.floatValue += dragAmount
                }
            }
        } else Modifier
    )
}