package raf.console.chitalka.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.domain.model.ButtonItem
import raf.console.chitalka.presentation.core.components.settings.SegmentedButtonWithTitle
import raf.console.chitalka.presentation.data.MainEvent
import raf.console.chitalka.presentation.data.MainViewModel
import raf.console.chitalka.presentation.screens.settings.nested.reader.data.ReaderTextAlignment

/**
 * Text Alignment setting.
 * Changes Reader's text alignment (Start/Justify/Center/End).
 */
@Composable
fun TextAlignmentSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SegmentedButtonWithTitle(
        title = stringResource(id = R.string.text_alignment_option),
        buttons = ReaderTextAlignment.entries.map {
            ButtonItem(
                id = it.toString(),
                title = when (it) {
                    ReaderTextAlignment.START -> stringResource(id = R.string.text_alignment_start)
                    ReaderTextAlignment.JUSTIFY -> stringResource(id = R.string.text_alignment_justify)
                    ReaderTextAlignment.CENTER -> stringResource(id = R.string.text_alignment_center)
                    ReaderTextAlignment.END -> stringResource(id = R.string.text_alignment_end)
                },
                textStyle = MaterialTheme.typography.labelLarge,
                selected = it == state.value.textAlignment
            )
        },
        onClick = {
            onMainEvent(
                MainEvent.OnChangeTextAlignment(
                    it.id
                )
            )
        }
    )
}