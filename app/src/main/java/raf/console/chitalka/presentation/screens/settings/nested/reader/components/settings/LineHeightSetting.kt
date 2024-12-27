package raf.console.chitalka.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.settings.SliderWithTitle
import raf.console.chitalka.presentation.data.MainEvent
import raf.console.chitalka.presentation.data.MainViewModel

/**
 * Line Height setting.
 * Changes Reader's line height.
 */
@Composable
fun LineHeightSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SliderWithTitle(
        value = state.value.lineHeight to "pt",
        fromValue = 1,
        toValue = 24,
        title = stringResource(id = R.string.line_height_option),
        onValueChange = {
            onMainEvent(
                MainEvent.OnChangeLineHeight(it)
            )
        }
    )
}