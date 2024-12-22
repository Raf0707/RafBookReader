package com.byteflipper.everbook.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.everbook.R
import com.byteflipper.everbook.presentation.core.components.settings.SwitchWithTitle
import com.byteflipper.everbook.presentation.data.MainEvent
import com.byteflipper.everbook.presentation.data.MainViewModel

/**
 * Check for Text Update setting.
 * If true, automatically checks whether there is book's text update.
 */
@Composable
fun CheckForTextUpdateSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SwitchWithTitle(
        selected = state.value.checkForTextUpdate,
        title = stringResource(id = R.string.check_for_text_update_option),
        description = stringResource(id = R.string.check_for_text_update_option_desc),
        onClick = {
            onMainEvent(
                MainEvent.OnChangeCheckForTextUpdate(
                    !state.value.checkForTextUpdate
                )
            )
        }
    )
}