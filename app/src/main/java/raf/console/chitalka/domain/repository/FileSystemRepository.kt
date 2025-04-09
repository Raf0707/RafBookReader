/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.repository

import raf.console.chitalka.domain.browse.SelectableFile
import raf.console.chitalka.domain.file.CachedFile
import raf.console.chitalka.domain.library.book.NullableBook

interface FileSystemRepository {

    suspend fun getFiles(
        query: String = ""
    ): List<SelectableFile>

    suspend fun getBookFromFile(
        cachedFile: CachedFile
    ): NullableBook
}