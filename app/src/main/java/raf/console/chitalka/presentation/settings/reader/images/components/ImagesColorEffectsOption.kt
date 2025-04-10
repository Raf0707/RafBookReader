/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf0707 for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.reader.images.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import raf.console.chitalka.R
import raf.console.chitalka.domain.reader.ReaderColorEffects
import raf.console.chitalka.domain.ui.ButtonItem
import raf.console.chitalka.presentation.core.components.settings.ChipsWithTitle
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.main.MainModel
import raf.console.chitalka.ui.theme.ExpandingTransition

@Composable
fun ImagesColorEffectsOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    ExpandingTransition(visible = state.value.images) {
        ChipsWithTitle(
            title = stringResource(id = R.string.images_color_effects_option),
            chips = ReaderColorEffects.entries.map {
                ButtonItem(
                    id = it.toString(),
                    title = when (it) {
                        ReaderColorEffects.OFF -> {
                            stringResource(R.string.color_effects_off)
                        }

                        ReaderColorEffects.GRAYSCALE -> {
                            stringResource(R.string.color_effects_grayscale)
                        }

                        ReaderColorEffects.FONT -> {
                            stringResource(R.string.color_effects_font)
                        }

                        ReaderColorEffects.BACKGROUND -> {
                            stringResource(R.string.color_effects_background)
                        }
                    },
                    textStyle = MaterialTheme.typography.labelLarge,
                    selected = it == state.value.imagesColorEffects
                )
            },
            onClick = {
                mainModel.onEvent(
                    MainEvent.OnChangeImagesColorEffects(
                        it.id
                    )
                )
            }
        )
    }
}