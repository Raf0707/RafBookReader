/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.data.mapper.book

import androidx.core.net.toUri
import raf.console.chitalka.R
import raf.console.chitalka.data.local.dto.BookEntity
import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.domain.ui.UIText
import raf.console.chitalka.domain.library.category.Category
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
            categoryId = if (book.categoryId != 0) book.categoryId else when (book.category) {
                Category.READING -> 1
                Category.ALREADY_READ -> 2
                Category.PLANNING -> 3
                Category.DROPPED -> 4
                else -> 0
            }
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
            categoryId = bookEntity.categoryId,
            category = when (bookEntity.categoryId) {
                1 -> Category.READING
                2 -> Category.ALREADY_READ
                3 -> Category.PLANNING
                4 -> Category.DROPPED
                else -> null
            },
            coverImage = if (bookEntity.image != null) bookEntity.image.toUri() else null
        )
    }
}