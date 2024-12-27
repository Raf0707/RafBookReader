package raf.console.chitalka.presentation.screens.settings.data

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Immutable
import raf.console.chitalka.domain.model.ColorPreset
import raf.console.chitalka.presentation.core.constants.Constants
import raf.console.chitalka.presentation.core.constants.provideDefaultColorPreset

@Immutable
data class SettingsState(
    val colorPresets: List<ColorPreset> = emptyList(),
    val selectedColorPreset: ColorPreset = Constants.provideDefaultColorPreset(),
    val animateColorPreset: Boolean = false,
    val colorPresetsListState: LazyListState = LazyListState()
)