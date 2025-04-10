/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf0707 for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.use_case.file_system

import raf.console.chitalka.domain.file.CachedFile
import raf.console.chitalka.domain.library.book.NullableBook
import raf.console.chitalka.domain.repository.FileSystemRepository
import java.io.File
import javax.inject.Inject

class GetBookFromFile @Inject constructor(
    private val repository: FileSystemRepository
) {

    suspend fun execute(cachedFile: CachedFile): NullableBook {
        return repository.getBookFromFile(cachedFile)
    }
}