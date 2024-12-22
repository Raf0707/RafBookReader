package com.byteflipper.everbook.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.everbook.R
import com.byteflipper.everbook.presentation.core.components.settings.SliderWithTitle
import com.byteflipper.everbook.presentation.data.MainEvent
import com.byteflipper.everbook.presentation.data.MainViewModel

/**
 * Perception Expander Thickness setting.
 * Changes thickness of the line.
 */
@Composable
fun PerceptionExpanderThicknessSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SliderWithTitle(
        value = state.value.perceptionExpanderThickness to "pt",
        fromValue = 1,
        toValue = 12,
        title = stringResource(id = R.string.perception_expander_thickness_option),
        onValueChange = {
            onMainEvent(
                MainEvent.OnChangePerceptionExpanderThickness(it)
            )
        }
    )
}