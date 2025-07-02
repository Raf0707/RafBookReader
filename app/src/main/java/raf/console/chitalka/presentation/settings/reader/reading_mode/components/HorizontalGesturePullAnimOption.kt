/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.reader.reading_mode.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import raf.console.chitalka.R
import raf.console.chitalka.domain.reader.ReaderHorizontalGesture
import raf.console.chitalka.presentation.core.components.settings.SwitchWithTitle
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.main.MainModel
import raf.console.chitalka.ui.theme.ExpandingTransition

@Composable
fun HorizontalGesturePullAnimOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    ExpandingTransition(
        visible = when (state.value.horizontalGesture) {
            ReaderHorizontalGesture.OFF -> false
            else -> true
        }
    ) {
        SwitchWithTitle(
            selected = state.value.horizontalGesturePullAnim,
            title = stringResource(id = R.string.horizontal_gesture_pull_anim_option),
            description = stringResource(id = R.string.horizontal_gesture_pull_anim_option_desc),
            onClick = {
                mainModel.onEvent(
                    MainEvent.OnChangeHorizontalGesturePullAnim(
                        !state.value.horizontalGesturePullAnim
                    )
                )
            }
        )
    }
}