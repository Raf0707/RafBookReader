package raf.console.chitalka.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.settings.SwitchWithTitle
import raf.console.chitalka.presentation.data.MainEvent
import raf.console.chitalka.presentation.data.MainViewModel

/**
 * Hide Bars On Fast Scroll setting.
 * If true, hides bars during fast scroll.
 */
@Composable
fun HideBarsOnFastScrollSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SwitchWithTitle(
        selected = state.value.hideBarsOnFastScroll,
        title = stringResource(id = R.string.hide_bars_on_fast_scroll_option)
    ) {
        onMainEvent(
            MainEvent.OnChangeHideBarsOnFastScroll(!state.value.hideBarsOnFastScroll)
        )
    }
}