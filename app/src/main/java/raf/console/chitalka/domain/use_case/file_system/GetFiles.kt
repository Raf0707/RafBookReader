/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.use_case.file_system

import raf.console.chitalka.domain.browse.SelectableFile
import raf.console.chitalka.domain.repository.FileSystemRepository
import javax.inject.Inject

class GetFiles @Inject constructor(
    private val repository: FileSystemRepository
) {

    suspend fun execute(query: String): List<SelectableFile> {
        return repository.getFiles(query)
    }
}