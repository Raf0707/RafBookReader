/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.reader.progress.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import raf.console.chitalka.R
import raf.console.chitalka.domain.reader.ReaderProgressCount
import raf.console.chitalka.domain.ui.ButtonItem
import raf.console.chitalka.presentation.core.components.settings.SegmentedButtonWithTitle
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.main.MainModel

@Composable
fun ProgressCountOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    SegmentedButtonWithTitle(
        title = stringResource(id = R.string.progress_count_option),
        buttons = ReaderProgressCount.entries.map {
            ButtonItem(
                id = it.toString(),
                title = when (it) {
                    ReaderProgressCount.PERCENTAGE -> stringResource(id = R.string.progress_count_percentage)
                    ReaderProgressCount.QUANTITY -> stringResource(id = R.string.progress_count_quantity)
                },
                textStyle = MaterialTheme.typography.labelLarge,
                selected = it == state.value.progressCount
            )
        },
        onClick = {
            mainModel.onEvent(
                MainEvent.OnChangeProgressCount(
                    it.id
                )
            )
        }
    )
}