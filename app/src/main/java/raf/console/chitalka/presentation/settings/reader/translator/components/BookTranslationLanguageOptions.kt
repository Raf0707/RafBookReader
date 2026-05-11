/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.presentation.settings.reader.translator.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import raf.console.chitalka.R
import raf.console.chitalka.domain.translation.BookTranslationLanguage
import raf.console.chitalka.domain.ui.ButtonItem
import raf.console.chitalka.domain.use_case.translation.GetBookTranslationLanguages
import raf.console.chitalka.presentation.core.components.settings.ChipsWithTitle
import raf.console.chitalka.ui.main.MainEvent
import raf.console.chitalka.ui.main.MainModel
import raf.console.chitalka.ui.main.MainState

@Composable
fun BookTranslationSourceLanguageOption() {
    BookTranslationLanguageOption(
        title = stringResource(id = R.string.book_translation_source_language_option),
        selectedLanguageTag = { it.bookTranslationSourceLanguage },
        onChange = { MainEvent.OnChangeBookTranslationSourceLanguage(it) }
    )
}

@Composable
fun BookTranslationTargetLanguageOption() {
    BookTranslationLanguageOption(
        title = stringResource(id = R.string.book_translation_target_language_option),
        selectedLanguageTag = { it.bookTranslationTargetLanguage },
        onChange = { MainEvent.OnChangeBookTranslationTargetLanguage(it) }
    )
}

@Composable
private fun BookTranslationLanguageOption(
    title: String,
    selectedLanguageTag: (MainState) -> String,
    onChange: (String) -> MainEvent
) {
    val mainModel = hiltViewModel<MainModel>()
    val state = mainModel.state.collectAsStateWithLifecycle()
    val languages = remember { GetBookTranslationLanguages().execute() }

    ChipsWithTitle(
        title = title,
        chips = languages.map { language ->
            language.toButtonItem(
                selected = language.languageTag == selectedLanguageTag(state.value)
            )
        },
        onClick = {
            mainModel.onEvent(onChange(it.id))
        }
    )
}

@Composable
private fun BookTranslationLanguage.toButtonItem(
    selected: Boolean
): ButtonItem {
    return ButtonItem(
        id = languageTag,
        title = displayName,
        textStyle = MaterialTheme.typography.labelLarge,
        selected = selected
    )
}
