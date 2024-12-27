package raf.console.chitalka.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.settings.SliderWithTitle
import raf.console.chitalka.presentation.data.MainEvent
import raf.console.chitalka.presentation.data.MainViewModel

/**
 * Paragraph Height setting.
 * Changes Reader's paragraph height.
 */
@Composable
fun ParagraphHeightSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SliderWithTitle(
        value = state.value.paragraphHeight to "pt",
        fromValue = 0,
        toValue = 36,
        title = stringResource(id = R.string.paragraph_height_option),
        onValueChange = {
            onMainEvent(
                MainEvent.OnChangeParagraphHeight(it)
            )
        }
    )
}