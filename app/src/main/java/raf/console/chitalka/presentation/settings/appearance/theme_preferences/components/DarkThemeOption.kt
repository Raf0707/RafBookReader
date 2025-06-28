/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.appearance.theme_preferences.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import raf.console.chitalka.R
import raf.console.chitalka.domain.ui.ButtonItem
import raf.console.chitalka.presentation.core.components.settings.SegmentedButtonWithTitle
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.main.MainModel
import raf.console.chitalka.domain.ui.DarkTheme

@Composable
fun DarkThemeOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    SegmentedButtonWithTitle(
        title = stringResource(id = R.string.dark_theme_option),
        buttons = DarkTheme.entries.map {
            ButtonItem(
                it.toString(),
                title = when (it) {
                    DarkTheme.OFF -> stringResource(id = R.string.dark_theme_off)
                    DarkTheme.ON -> stringResource(id = R.string.dark_theme_on)
                    DarkTheme.FOLLOW_SYSTEM -> stringResource(id = R.string.dark_theme_follow_system)
                },
                textStyle = MaterialTheme.typography.labelLarge,
                selected = it == state.value.darkTheme
            )
        }
    ) {
        mainModel.onEvent(MainEvent.OnChangeDarkTheme(it.id))
    }
}