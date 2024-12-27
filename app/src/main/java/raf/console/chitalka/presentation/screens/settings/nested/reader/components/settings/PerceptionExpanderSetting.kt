package raf.console.chitalka.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.settings.SwitchWithTitle
import raf.console.chitalka.presentation.data.MainEvent
import raf.console.chitalka.presentation.data.MainViewModel

/**
 * Perception Expander setting.
 * If true, shows vertical lines in Reader, which makes you read faster.
 */
@Composable
fun PerceptionExpanderSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SwitchWithTitle(
        selected = state.value.perceptionExpander,
        title = stringResource(id = R.string.perception_expander_option),
        description = stringResource(id = R.string.perception_expander_option_desc),
        onClick = {
            onMainEvent(
                MainEvent.OnChangePerceptionExpander(
                    !state.value.perceptionExpander
                )
            )
        }
    )
}