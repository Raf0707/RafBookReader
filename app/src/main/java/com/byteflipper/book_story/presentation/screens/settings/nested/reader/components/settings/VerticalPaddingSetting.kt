package com.byteflipper.book_story.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.book_story.R
import com.byteflipper.book_story.presentation.core.components.settings.SliderWithTitle
import com.byteflipper.book_story.presentation.data.MainEvent
import com.byteflipper.book_story.presentation.data.MainViewModel

/**
 * Vertical Padding setting.
 * Changes Reader's vertical padding.
 */
@Composable
fun VerticalPaddingSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SliderWithTitle(
        value = state.value.verticalPadding to "pt",
        fromValue = 0,
        toValue = 24,
        title = stringResource(id = R.string.vertical_padding_option),
        onValueChange = {
            onMainEvent(
                MainEvent.OnChangeVerticalPadding(it)
            )
        }
    )
}