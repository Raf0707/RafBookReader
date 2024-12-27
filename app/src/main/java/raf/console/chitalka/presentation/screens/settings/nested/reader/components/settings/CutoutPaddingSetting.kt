package raf.console.chitalka.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.settings.SwitchWithTitle
import raf.console.chitalka.presentation.data.MainEvent
import raf.console.chitalka.presentation.data.MainViewModel

/**
 * Cutout Padding setting.
 * If true, applies padding to cutout area.
 */
@Composable
fun CutoutPaddingSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SwitchWithTitle(
        selected = state.value.cutoutPadding,
        title = stringResource(id = R.string.cutout_padding_option),
        description = stringResource(id = R.string.cutout_padding_option_desc),
        onClick = {
            onMainEvent(
                MainEvent.OnChangeCutoutPadding(
                    !state.value.cutoutPadding
                )
            )
        }
    )
}