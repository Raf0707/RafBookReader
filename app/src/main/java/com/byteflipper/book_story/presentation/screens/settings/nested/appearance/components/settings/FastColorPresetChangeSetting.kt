package com.byteflipper.book_story.presentation.screens.settings.nested.appearance.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.book_story.R
import com.byteflipper.book_story.presentation.core.components.settings.SwitchWithTitle
import com.byteflipper.book_story.presentation.data.MainEvent
import com.byteflipper.book_story.presentation.data.MainViewModel

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