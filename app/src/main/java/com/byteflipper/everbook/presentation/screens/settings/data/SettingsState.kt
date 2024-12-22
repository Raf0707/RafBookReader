package com.byteflipper.everbook.presentation.screens.settings.data

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Immutable
import com.byteflipper.everbook.domain.model.ColorPreset
import com.byteflipper.everbook.presentation.core.constants.Constants
import com.byteflipper.everbook.presentation.core.constants.provideDefaultColorPreset

@Immutable
data class SettingsState(
    val colorPresets: List<ColorPreset> = emptyList(),
    val selectedColorPreset: ColorPreset = Constants.provideDefaultColorPreset(),
    val animateColorPreset: Boolean = false,
    val colorPresetsListState: LazyListState = LazyListState()
)