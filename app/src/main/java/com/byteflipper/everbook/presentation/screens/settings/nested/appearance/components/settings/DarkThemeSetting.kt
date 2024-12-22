package com.byteflipper.everbook.presentation.screens.settings.nested.appearance.components.settings

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.everbook.R
import com.byteflipper.everbook.domain.model.ButtonItem
import com.byteflipper.everbook.presentation.core.components.settings.SegmentedButtonWithTitle
import com.byteflipper.everbook.presentation.data.MainEvent
import com.byteflipper.everbook.presentation.data.MainViewModel
import com.byteflipper.everbook.presentation.ui.DarkTheme

/**
 * Dark Theme setting.
 * If true, dark theme applied to the app's theme.
 */
@Composable
fun DarkThemeSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SegmentedButtonWithTitle(
        title = stringResource(id = R.string.dark_theme_option),
        buttons = DarkTheme.entries.map {
            ButtonItem(
                it.toString(),
                title = when (it) {
                    DarkTheme.OFF -> stringResource(id = R.string.dark_theme_off)
                    DarkTheme.ON -> stringResource(id = R.string.dark_theme_on)
                    DarkTheme.FOLLOW_SYSTEM -> stringResource(id = R.string.dark_theme_follow_system)
                },
                textStyle = MaterialTheme.typography.labelLarge,
                selected = it == state.value.darkTheme
            )
        }
    ) {
        onMainEvent(
            MainEvent.OnChangeDarkTheme(
                it.id
            )
        )
    }
}