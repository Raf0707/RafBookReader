package com.byteflipper.everbook.presentation.screens.settings.nested.appearance.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.byteflipper.everbook.R
import com.byteflipper.everbook.presentation.core.components.settings.SwitchWithTitle
import com.byteflipper.everbook.presentation.data.MainEvent
import com.byteflipper.everbook.presentation.data.MainViewModel
import com.byteflipper.everbook.presentation.ui.ExpandingTransition
import com.byteflipper.everbook.presentation.ui.isDark
import com.byteflipper.everbook.presentation.ui.isPureDark

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