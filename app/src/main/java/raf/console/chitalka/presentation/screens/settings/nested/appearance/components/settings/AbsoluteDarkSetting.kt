package raf.console.chitalka.presentation.screens.settings.nested.appearance.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.settings.SwitchWithTitle
import raf.console.chitalka.presentation.data.MainEvent
import raf.console.chitalka.presentation.data.MainViewModel
import raf.console.chitalka.presentation.ui.ExpandingTransition
import raf.console.chitalka.presentation.ui.isDark
import raf.console.chitalka.presentation.ui.isPureDark

/**
 * Absolute Dark setting.
 * If true, changes Pure Dark (OLED) theme to have black color as background.
 */
@Composable
fun AbsoluteDarkSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()
    val context = LocalContext.current

    ExpandingTransition(
        visible = state.value.pureDark.isPureDark(context)
                && state.value.darkTheme.isDark()
    ) {
        SwitchWithTitle(
            selected = state.value.absoluteDark,
            title = stringResource(id = R.string.absolute_dark_option),
            description = stringResource(id = R.string.absolute_dark_option_desc),
            onClick = {
                onMainEvent(
                    MainEvent.OnChangeAbsoluteDark(
                        !state.value.absoluteDark
                    )
                )
            }
        )
    }
}