/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.repository

import raf.console.chitalka.domain.translation.BookTranslationCacheKey

interface BookTranslationRepository {

    suspend fun getFileFingerprint(filePath: String): String

    suspend fun readTranslation(key: BookTranslationCacheKey): Map<Int, String>?

    suspend fun writeTranslation(
        key: BookTranslationCacheKey,
        translatedTextByIndex: Map<Int, String>
    )
}
