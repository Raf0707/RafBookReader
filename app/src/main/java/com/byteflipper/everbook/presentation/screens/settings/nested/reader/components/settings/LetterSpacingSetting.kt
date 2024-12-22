package com.byteflipper.everbook.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.everbook.R
import com.byteflipper.everbook.presentation.core.components.settings.SliderWithTitle
import com.byteflipper.everbook.presentation.data.MainEvent
import com.byteflipper.everbook.presentation.data.MainViewModel

/**
 * Letter Spacing setting.
 * Changes Reader's letter spacing.
 */
@Composable
fun LetterSpacingSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SliderWithTitle(
        value = state.value.letterSpacing to "pt",
        fromValue = -8,
        toValue = 16,
        title = stringResource(id = R.string.letter_spacing_option),
        onValueChange = {
            onMainEvent(
                MainEvent.OnChangeLetterSpacing(it)
            )
        }
    )
}