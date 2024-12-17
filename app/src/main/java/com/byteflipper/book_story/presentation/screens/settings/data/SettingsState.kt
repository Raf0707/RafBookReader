package com.byteflipper.book_story.presentation.screens.settings.data

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Immutable
import com.byteflipper.book_story.domain.model.ColorPreset
import com.byteflipper.book_story.presentation.core.constants.Constants
import com.byteflipper.book_story.presentation.core.constants.provideDefaultColorPreset

@Immutable
data class SettingsState(
    val colorPresets: List<ColorPreset> = emptyList(),
    val selectedColorPreset: ColorPreset = Constants.provideDefaultColorPreset(),
    val animateColorPreset: Boolean = false,
    val colorPresetsListState: LazyListState = LazyListState()
)