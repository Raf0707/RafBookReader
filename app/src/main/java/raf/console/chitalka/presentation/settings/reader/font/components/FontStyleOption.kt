/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.reader.font.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import raf.console.chitalka.R
import raf.console.chitalka.domain.ui.ButtonItem
import raf.console.chitalka.presentation.core.components.settings.SegmentedButtonWithTitle
import raf.console.chitalka.presentation.core.constants.provideFonts
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.main.MainModel

@Composable
fun FontStyleOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    val fontFamily = remember(state.value.fontFamily) {
        provideFonts().run {
            find {
                it.id == state.value.fontFamily
            } ?: get(0)
        }
    }

    SegmentedButtonWithTitle(
        title = stringResource(id = R.string.font_style_option),
        buttons = listOf(
            ButtonItem(
                id = "normal",
                title = stringResource(id = R.string.font_style_normal),
                textStyle = MaterialTheme.typography.labelLarge.copy(
                    fontFamily = fontFamily.font,
                    fontStyle = FontStyle.Normal
                ),
                selected = !state.value.isItalic
            ),
            ButtonItem(
                id = "italic",
                title = stringResource(id = R.string.font_style_italic),
                textStyle = MaterialTheme.typography.labelLarge.copy(
                    fontFamily = fontFamily.font,
                    fontStyle = FontStyle.Italic
                ),
                selected = state.value.isItalic
            )
        ),
        onClick = {
            mainModel.onEvent(
                MainEvent.OnChangeFontStyle(
                    when (it.id) {
                        "italic" -> true
                        else -> false
                    }
                )
            )
        }
    )
}