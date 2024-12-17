package com.byteflipper.book_story.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.book_story.R
import com.byteflipper.book_story.presentation.core.components.settings.SwitchWithTitle
import com.byteflipper.book_story.presentation.data.MainEvent
import com.byteflipper.book_story.presentation.data.MainViewModel

/**
 * Fullscreen setting.
 * If true, hides system bars in Reader.
 */
@Composable
fun FullscreenSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SwitchWithTitle(
        selected = state.value.fullscreen,
        title = stringResource(id = R.string.fullscreen_option),
        description = stringResource(id = R.string.fullscreen_option_desc),
        onClick = {
            onMainEvent(
                MainEvent.OnChangeFullscreen(
                    !state.value.fullscreen
                )
            )
        }
    )
}