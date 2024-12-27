package raf.console.chitalka.presentation.screens.settings.nested.general.components.settings

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.settings.SwitchWithTitle
import raf.console.chitalka.presentation.data.MainEvent
import raf.console.chitalka.presentation.data.MainViewModel

/**
 * Double Press Exit setting.
 * If true, to close the app you need to press back twice.
 */
@SuppressLint("InlinedApi")
@Composable
fun DoublePressExitSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SwitchWithTitle(
        selected = state.value.doublePressExit,
        title = stringResource(id = R.string.double_press_exit_option),
        description = stringResource(id = R.string.double_press_exit_option_desc)
    ) {
        onMainEvent(
            MainEvent.OnChangeDoublePressExit(!state.value.doublePressExit)
        )
    }
}