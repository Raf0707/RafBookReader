package com.byteflipper.book_story.presentation.screens.settings.nested.appearance.components.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.byteflipper.book_story.R
import com.byteflipper.book_story.presentation.core.components.settings.SwitchWithTitle
import com.byteflipper.book_story.presentation.data.MainEvent
import com.byteflipper.book_story.presentation.data.MainViewModel
import com.byteflipper.book_story.presentation.ui.ExpandingTransition
import com.byteflipper.book_story.presentation.ui.isDark
import com.byteflipper.book_story.presentation.ui.isPureDark

/**
 * Absolute Dark setting.
 * If true, changes Pure Dark (OLED) theme to have black color as background.
 */
@Composable
fun AbsoluteDarkSetting() {
    val state = MainViewModel.getState()
    val onMainEvent = MainViewModel.getEvent()
    val context = LocalContext.current

    ExpandingTransition(
        visible = state.value.pureDark.isPureDark(context)
                && state.value.darkTheme.isDark()
    ) {
        SwitchWithTitle(
            selected = state.value.absoluteDark,
            title = stringResource(id = R.string.absolute_dark_option),
            description = stringResource(id = R.string.absolute_dark_option_desc),
            onClick = {
                onMainEvent(
                    MainEvent.OnChangeAbsoluteDark(
                        !state.value.absoluteDark
                    )
                )
            }
        )
    }
}