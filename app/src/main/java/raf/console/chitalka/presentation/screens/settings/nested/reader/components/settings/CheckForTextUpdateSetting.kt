package raf.console.chitalka.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.settings.SwitchWithTitle
import raf.console.chitalka.presentation.data.MainEvent
import raf.console.chitalka.presentation.data.MainViewModel

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