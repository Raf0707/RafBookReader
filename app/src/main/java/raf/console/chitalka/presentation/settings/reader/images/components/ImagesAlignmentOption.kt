/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.reader.images.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import raf.console.chitalka.R
import raf.console.chitalka.domain.ui.ButtonItem
import raf.console.chitalka.domain.util.HorizontalAlignment
import raf.console.chitalka.presentation.core.components.settings.SegmentedButtonWithTitle
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.main.MainModel
import raf.console.chitalka.ui.theme.ExpandingTransition

@Composable
fun ImagesAlignmentOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    ExpandingTransition(visible = state.value.images) {
        SegmentedButtonWithTitle(
            title = stringResource(id = R.string.images_alignment_option),
            buttons = HorizontalAlignment.entries.map {
                ButtonItem(
                    id = it.toString(),
                    title = when (it) {
                        HorizontalAlignment.START -> stringResource(id = R.string.alignment_start)
                        HorizontalAlignment.CENTER -> stringResource(id = R.string.alignment_center)
                        HorizontalAlignment.END -> stringResource(id = R.string.alignment_end)
                    },
                    textStyle = MaterialTheme.typography.labelLarge,
                    selected = it == state.value.imagesAlignment
                )
            },
            onClick = {
                mainModel.onEvent(
                    MainEvent.OnChangeImagesAlignment(
                        it.id
                    )
                )
            }
        )
    }
}