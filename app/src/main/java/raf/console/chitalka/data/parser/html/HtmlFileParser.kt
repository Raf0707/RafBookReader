/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.data.parser.html

import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import raf.console.chitalka.R
import raf.console.chitalka.data.parser.FileParser
import raf.console.chitalka.domain.file.CachedFile
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.domain.library.book.BookWithCover
import raf.console.chitalka.domain.library.category.Category
import raf.console.chitalka.domain.ui.UIText
import javax.inject.Inject

class HtmlFileParser @Inject constructor() : FileParser {

    override suspend fun parse(cachedFile: CachedFile): BookWithCover? {
        return try {
            val document = cachedFile.openInputStream()?.use {
                Jsoup.parse(it, null, "", Parser.htmlParser())
            }

            val title = document?.select("head > title")?.text()?.trim().run {
                if (isNullOrBlank()) {
                    return@run cachedFile.name.substringBeforeLast(".").trim()
                }
                return@run this
            }

            BookWithCover(
                book = Book(
                    title = title,
                    author = UIText.StringResource(R.string.unknown_author),
                    description = null,
                    scrollIndex = 0,
                    scrollOffset = 0,
                    progress = 0f,
                    filePath = cachedFile.path,
                    lastOpened = null,
                    category = Category.entries[0],
                    coverImage = null
                ),
                coverImage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}