package com.byteflipper.everbook.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.everbook.R
import com.byteflipper.everbook.presentation.core.components.settings.SwitchWithTitle
import com.byteflipper.everbook.presentation.data.MainEvent
import com.byteflipper.everbook.presentation.data.MainViewModel

/**
 * Fullscreen setting.
 * If true, hides system bars in Reader.
 */
@Composable
fun FullscreenSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SwitchWithTitle(
        selected = state.value.fullscreen,
        title = stringResource(id = R.string.fullscreen_option),
        description = stringResource(id = R.string.fullscreen_option_desc),
        onClick = {
            onMainEvent(
                MainEvent.OnChangeFullscreen(
                    !state.value.fullscreen
                )
            )
        }
    )
}