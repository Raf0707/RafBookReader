package raf.console.chitalka.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import raf.console.chitalka.R
import raf.console.chitalka.domain.model.ButtonItem
import raf.console.chitalka.presentation.core.components.settings.ChipsWithTitle
import raf.console.chitalka.presentation.core.constants.Constants
import raf.console.chitalka.presentation.core.constants.provideFonts
import raf.console.chitalka.presentation.data.MainEvent
import raf.console.chitalka.presentation.data.MainViewModel

/**
 * Font Family setting.
 * Changes Reader's font, uses fonts from [provideFonts].
 */
@Composable
fun FontFamilySetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    val fontFamily = remember(state.value.fontFamily) {
        Constants.provideFonts(withRandom = true).find {
            it.id == state.value.fontFamily
        } ?: Constants.provideFonts(withRandom = false)[0]
    }

    ChipsWithTitle(
        title = stringResource(id = R.string.font_family_option),
        chips = Constants.provideFonts(withRandom = true)
            .map {
                ButtonItem(
                    id = it.id,
                    title = it.fontName.asString(),
                    textStyle = MaterialTheme.typography.labelLarge.copy(
                        fontFamily = when (it.id) {
                            "random" -> FontFamily.Default
                            else -> it.font
                        }
                    ),
                    selected = it.id == fontFamily.id
                )
            },
        onClick = {
            onMainEvent(MainEvent.OnChangeFontFamily(it.id))
        }
    )
}