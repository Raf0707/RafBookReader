/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.general.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import raf.console.chitalka.R
import raf.console.chitalka.domain.ui.ButtonItem
import raf.console.chitalka.presentation.core.components.settings.ChipsWithTitle
import raf.console.chitalka.presentation.core.constants.provideLanguages
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.main.MainModel

@Composable
fun AppLanguageOption() {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()

    ChipsWithTitle(
        title = stringResource(id = R.string.language_option),
        chips = provideLanguages().sortedBy { it.second }.map {
            ButtonItem(
                it.first,
                it.second,
                MaterialTheme.typography.labelLarge,
                it.first == state.value.language
            )
        }.sortedBy { it.title }
    ) {
        mainModel.onEvent(MainEvent.OnChangeLanguage(it.id))
    }
}