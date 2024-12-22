package com.byteflipper.everbook.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.everbook.R
import com.byteflipper.everbook.presentation.core.components.settings.SliderWithTitle
import com.byteflipper.everbook.presentation.data.MainEvent
import com.byteflipper.everbook.presentation.data.MainViewModel

/**
 * Side Padding setting.
 * Changes Reader's side padding.
 */
@Composable
fun SidePaddingSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SliderWithTitle(
        value = state.value.sidePadding to "pt",
        fromValue = 1,
        toValue = 20,
        title = stringResource(id = R.string.side_padding_option),
        onValueChange = {
            onMainEvent(
                MainEvent.OnChangeSidePadding(it)
            )
        }
    )
}