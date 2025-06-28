/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.data.parser

import raf.console.chitalka.domain.file.CachedFile
import raf.console.chitalka.domain.reader.ReaderText
import java.io.File

interface TextParser {
    suspend fun parse(cachedFile: CachedFile): List<ReaderText>
}