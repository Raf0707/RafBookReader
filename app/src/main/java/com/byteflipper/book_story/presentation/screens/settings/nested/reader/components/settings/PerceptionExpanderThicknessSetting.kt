package com.byteflipper.book_story.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.book_story.R
import com.byteflipper.book_story.presentation.core.components.settings.SliderWithTitle
import com.byteflipper.book_story.presentation.data.MainEvent
import com.byteflipper.book_story.presentation.data.MainViewModel

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