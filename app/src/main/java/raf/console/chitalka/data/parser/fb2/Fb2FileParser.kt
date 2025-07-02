/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.data.parser.fb2

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

class Fb2FileParser @Inject constructor() : FileParser {

    override suspend fun parse(cachedFile: CachedFile): BookWithCover? {
        return try {
            val document = cachedFile.openInputStream()?.use {
                Jsoup.parse(it, null, "", Parser.xmlParser())
            }

            val title = document?.selectFirst("book-title")?.text()?.trim().run {
                if (isNullOrBlank()) {
                    return@run cachedFile.name.substringBeforeLast(".").trim()
                }
                this
            }

            val author = document?.selectFirst("author")?.text()?.trim().run {
                if (isNullOrBlank()) {
                    return@run UIText.StringResource(R.string.unknown_author)
                }
                UIText.StringValue(this.trim())
            }

            val description = document?.selectFirst("annotation")?.text()?.trim().run {
                if (isNullOrBlank()) {
                    return@run null
                }
                this
            }

            BookWithCover(
                book = Book(
                    title = title,
                    author = author,
                    description = description,
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