/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.reader

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import raf.console.chitalka.domain.util.Selected

@Immutable
data class ColorPreset(
    val id: Int,
    val name: String?,
    val backgroundColor: Color,
    val fontColor: Color,
    val isSelected: Selected
)