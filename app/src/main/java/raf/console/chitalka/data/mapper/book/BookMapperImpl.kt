/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.data.mapper.book

import androidx.core.net.toUri
import raf.console.chitalka.R
import raf.console.chitalka.data.local.dto.BookEntity
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.domain.ui.UIText
import javax.inject.Inject

class BookMapperImpl @Inject constructor() : BookMapper {
    override suspend fun toBookEntity(book: Book): BookEntity {
        return BookEntity(
            id = book.id,
            title = book.title,
            filePath = book.filePath,
            scrollIndex = book.scrollIndex,
            scrollOffset = book.scrollOffset,
            progress = book.progress,
            author = book.author.getAsString(),
            description = book.description,
            image = book.coverImage?.toString(),
            category = book.category
        )
    }

    override suspend fun toBook(bookEntity: BookEntity): Book {
        return Book(
            id = bookEntity.id,
            title = bookEntity.title,
            author = bookEntity.author?.let { UIText.StringValue(it) } ?: UIText.StringResource(
                R.string.unknown_author
            ),
            description = bookEntity.description,
            scrollIndex = bookEntity.scrollIndex,
            scrollOffset = bookEntity.scrollOffset,
            progress = bookEntity.progress,
            filePath = bookEntity.filePath,
            lastOpened = null,
            category = bookEntity.category,
            coverImage = if (bookEntity.image != null) bookEntity.image.toUri() else null
        )
    }
}