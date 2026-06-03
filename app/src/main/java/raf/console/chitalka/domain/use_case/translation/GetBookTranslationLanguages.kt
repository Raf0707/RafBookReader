/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.use_case.translation

import com.google.mlkit.nl.translate.TranslateLanguage
import raf.console.chitalka.domain.translation.BookTranslationLanguage
import raf.console.chitalka.presentation.core.constants.provideLanguages
import javax.inject.Inject

class GetBookTranslationLanguages @Inject constructor() {

    fun execute(): List<BookTranslationLanguage> {
        val mlKitLanguages = TranslateLanguage.getAllLanguages().toSet()

        return provideLanguages().mapNotNull { (languageTag, displayName) ->
            val mlKitTag = TranslateLanguage.fromLanguageTag(languageTag) ?: return@mapNotNull null
            if (mlKitTag !in mlKitLanguages) return@mapNotNull null

            BookTranslationLanguage(
                languageTag = languageTag,
                mlKitTag = mlKitTag,
                displayName = displayName
            )
        }
    }
}
