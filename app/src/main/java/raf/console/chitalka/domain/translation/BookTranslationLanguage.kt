/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.translation

import androidx.compose.runtime.Immutable

@Immutable
data class BookTranslationLanguage(
    val languageTag: String,
    val mlKitTag: String,
    val displayName: String
)
