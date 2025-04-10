/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf0707 for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.reader.reading_speed.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.settings.SliderWithTitle
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.main.MainModel
import raf.console.chitalka.ui.theme.ExpandingTransition

@Composable
fun HighlightedReadingThicknessOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    ExpandingTransition(visible = state.value.highlightedReading) {
        SliderWithTitle(
            value = state.value.highlightedReadingThickness
                    to " ${stringResource(R.string.highlighted_reading_level)}",
            fromValue = 1,
            toValue = 3,
            title = stringResource(id = R.string.highlighted_reading_thickness_option),
            onValueChange = {
                mainModel.onEvent(
                    MainEvent.OnChangeHighlightedReadingThickness(it)
                )
            }
        )
    }
}