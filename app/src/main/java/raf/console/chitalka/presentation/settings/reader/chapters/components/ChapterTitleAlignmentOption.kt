/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.reader.chapters.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import raf.console.chitalka.R
import raf.console.chitalka.domain.reader.ReaderTextAlignment
import raf.console.chitalka.domain.ui.ButtonItem
import raf.console.chitalka.presentation.core.components.settings.SegmentedButtonWithTitle
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.main.MainModel

@Composable
fun ChapterTitleAlignmentOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    SegmentedButtonWithTitle(
        title = stringResource(id = R.string.chapter_title_alignment_option),
        buttons = ReaderTextAlignment.entries.map {
            ButtonItem(
                id = it.toString(),
                title = when (it) {
                    ReaderTextAlignment.START -> stringResource(id = R.string.alignment_start)
                    ReaderTextAlignment.JUSTIFY -> stringResource(id = R.string.alignment_justify)
                    ReaderTextAlignment.CENTER -> stringResource(id = R.string.alignment_center)
                    ReaderTextAlignment.END -> stringResource(id = R.string.alignment_end)
                },
                textStyle = MaterialTheme.typography.labelLarge,
                selected = it == state.value.chapterTitleAlignment
            )
        },
        onClick = {
            mainModel.onEvent(
                MainEvent.OnChangeChapterTitleAlignment(
                    it.id
                )
            )
        }
    )
}