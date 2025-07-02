/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.reader.misc.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.settings.SwitchWithTitle
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.main.MainModel

@Composable
fun HideBarsOnFastScrollOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    SwitchWithTitle(
        selected = state.value.hideBarsOnFastScroll,
        title = stringResource(id = R.string.hide_bars_on_fast_scroll_option)
    ) {
        mainModel.onEvent(
            MainEvent.OnChangeHideBarsOnFastScroll(!state.value.hideBarsOnFastScroll)
        )
    }
}