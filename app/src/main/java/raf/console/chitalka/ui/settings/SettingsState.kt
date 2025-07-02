/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.ui.settings

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Immutable
import raf.console.chitalka.domain.reader.ColorPreset
import raf.console.chitalka.presentation.core.constants.provideDefaultColorPreset

@Immutable
data class SettingsState(
    val colorPresets: List<ColorPreset> = emptyList(),
    val selectedColorPreset: ColorPreset = provideDefaultColorPreset(),
    val animateColorPreset: Boolean = false,
    val colorPresetListState: LazyListState = LazyListState()
)