package com.byteflipper.everbook.presentation.screens.settings.nested.appearance.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.everbook.R
import com.byteflipper.everbook.presentation.core.components.settings.SwitchWithTitle
import com.byteflipper.everbook.presentation.data.MainEvent
import com.byteflipper.everbook.presentation.data.MainViewModel

/**
 * Fast Color Preset Change setting.
 * If true, user can fast change color presets in Reader with swipes.
 */
@Composable
fun FastColorPresetChangeSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SwitchWithTitle(
        selected = state.value.fastColorPresetChange,
        title = stringResource(id = R.string.fast_color_preset_change_option),
        description = stringResource(id = R.string.fast_color_preset_change_option_desc),
        onClick = {
            onMainEvent(
                MainEvent.OnChangeFastColorPresetChange(
                    !state.value.fastColorPresetChange
                )
            )
        }
    )
}