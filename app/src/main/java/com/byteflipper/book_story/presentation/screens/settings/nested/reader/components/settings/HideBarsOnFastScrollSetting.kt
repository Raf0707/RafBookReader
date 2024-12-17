package com.byteflipper.book_story.presentation.screens.settings.nested.reader.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.byteflipper.book_story.R
import com.byteflipper.book_story.presentation.core.components.settings.SwitchWithTitle
import com.byteflipper.book_story.presentation.data.MainEvent
import com.byteflipper.book_story.presentation.data.MainViewModel

/**
 * Hide Bars On Fast Scroll setting.
 * If true, hides bars during fast scroll.
 */
@Composable
fun HideBarsOnFastScrollSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()

    SwitchWithTitle(
        selected = state.value.hideBarsOnFastScroll,
        title = stringResource(id = R.string.hide_bars_on_fast_scroll_option)
    ) {
        onMainEvent(
            MainEvent.OnChangeHideBarsOnFastScroll(!state.value.hideBarsOnFastScroll)
        )
    }
}