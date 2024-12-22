package com.byteflipper.everbook.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.everbook.R
import com.byteflipper.everbook.presentation.core.components.settings.SwitchWithTitle
import com.byteflipper.everbook.presentation.data.MainEvent
import com.byteflipper.everbook.presentation.data.MainViewModel

/**
 * Custom Screen Brightness setting.
 * If true, applies custom brightness in Reader.
 */
@Composable
fun CustomScreenBrightnessSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SwitchWithTitle(
        selected = state.value.customScreenBrightness,
        title = stringResource(id = R.string.custom_screen_brightness_option),
        onClick = {
            onMainEvent(
                MainEvent.OnChangeCustomScreenBrightness(
                    !state.value.customScreenBrightness
                )
            )
        }
    )
}