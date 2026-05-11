/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.reader.translator.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import raf.console.chitalka.R
import raf.console.chitalka.presentation.core.components.settings.SwitchWithTitle
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.main.MainModel

@Composable
fun BookTranslationProgressInBottomBarOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    SwitchWithTitle(
        selected = state.value.bookTranslationProgressInBottomBar,
        title = stringResource(id = R.string.book_translation_progress_in_bottom_bar_option),
        description = stringResource(id = R.string.book_translation_progress_in_bottom_bar_option_desc),
        onClick = {
            mainModel.onEvent(
                MainEvent.OnChangeBookTranslationProgressInBottomBar(
                    !state.value.bookTranslationProgressInBottomBar
                )
            )
        }
    )
}

@Composable
fun BookTranslationPartialNoticeOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    SwitchWithTitle(
        selected = state.value.bookTranslationPartialNotice,
        title = stringResource(id = R.string.book_translation_partial_notice_option),
        description = stringResource(id = R.string.book_translation_partial_notice_option_desc),
        onClick = {
            mainModel.onEvent(
                MainEvent.OnChangeBookTranslationPartialNotice(
                    !state.value.bookTranslationPartialNotice
                )
            )
        }
    )
}

@Composable
fun BookTranslationKeepPartialOnCancelOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    SwitchWithTitle(
        selected = state.value.bookTranslationKeepPartialOnCancel,
        title = stringResource(id = R.string.book_translation_keep_partial_on_cancel_option),
        description = stringResource(id = R.string.book_translation_keep_partial_on_cancel_option_desc),
        onClick = {
            mainModel.onEvent(
                MainEvent.OnChangeBookTranslationKeepPartialOnCancel(
                    !state.value.bookTranslationKeepPartialOnCancel
                )
            )
        }
    )
}
