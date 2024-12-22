package com.byteflipper.everbook.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.everbook.R
import com.byteflipper.everbook.presentation.core.components.settings.SwitchWithTitle
import com.byteflipper.everbook.presentation.data.MainEvent
import com.byteflipper.everbook.presentation.data.MainViewModel

/**
 * Double Click Translation setting.
 * Changes Reader's double click translation.
 */
@Composable
fun DoubleClickTranslationSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SwitchWithTitle(
        selected = state.value.doubleClickTranslation,
        title = stringResource(id = R.string.double_click_translation_option),
        description = stringResource(id = R.string.double_click_translation_option_desc),
        onClick = {
            onMainEvent(
                MainEvent.OnChangeDoubleClickTranslation(
                    !state.value.doubleClickTranslation
                )
            )
        }
    )
}