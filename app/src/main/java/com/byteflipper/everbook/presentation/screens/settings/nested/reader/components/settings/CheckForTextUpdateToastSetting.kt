package com.byteflipper.everbook.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.everbook.R
import com.byteflipper.everbook.presentation.core.components.settings.SwitchWithTitle
import com.byteflipper.everbook.presentation.data.MainEvent
import com.byteflipper.everbook.presentation.data.MainViewModel
import com.byteflipper.everbook.presentation.ui.ExpandingTransition

/**
 * Check for Text Update Toast setting.
 * If true, shows a toast when the text is up-to-date.
 */
@Composable
fun CheckForTextUpdateToastSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    ExpandingTransition(visible = state.value.checkForTextUpdate) {
        SwitchWithTitle(
            selected = state.value.checkForTextUpdateToast,
            title = stringResource(id = R.string.check_for_text_update_toast_option),
            onClick = {
                onMainEvent(
                    MainEvent.OnChangeCheckForTextUpdateToast(
                        !state.value.checkForTextUpdateToast
                    )
                )
            }
        )
    }
}