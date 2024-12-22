package com.byteflipper.everbook.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.everbook.R
import com.byteflipper.everbook.domain.model.ButtonItem
import com.byteflipper.everbook.presentation.core.components.settings.ChipsWithTitle
import com.byteflipper.everbook.presentation.data.MainEvent
import com.byteflipper.everbook.presentation.data.MainViewModel
import com.byteflipper.everbook.presentation.screens.settings.nested.reader.data.ReaderScreenOrientation

/**
 * Screen Orientation setting.
 * Changes Reader's screen orientation.
 */
@Composable
fun ScreenOrientationSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    ChipsWithTitle(
        title = stringResource(id = R.string.screen_orientation_option),
        chips = ReaderScreenOrientation.entries.map {
            ButtonItem(
                id = it.toString(),
                title = when (it.code) {
                    ReaderScreenOrientation.FREE.code -> stringResource(id = R.string.screen_orientation_free)
                    ReaderScreenOrientation.PORTRAIT.code -> stringResource(id = R.string.screen_orientation_portrait)
                    ReaderScreenOrientation.LANDSCAPE.code -> stringResource(id = R.string.screen_orientation_landscape)
                    ReaderScreenOrientation.LOCKED_PORTRAIT.code -> stringResource(id = R.string.screen_orientation_locked_portrait)
                    ReaderScreenOrientation.LOCKED_LANDSCAPE.code -> stringResource(id = R.string.screen_orientation_locked_landscape)
                    else -> stringResource(id = R.string.default_string)
                },
                textStyle = MaterialTheme.typography.labelLarge,
                selected = it == state.value.screenOrientation
            )
        },
        onClick = {
            onMainEvent(
                MainEvent.OnChangeScreenOrientation(
                    it.id
                )
            )
        }
    )
}