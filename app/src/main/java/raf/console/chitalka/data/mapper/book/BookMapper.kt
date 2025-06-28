/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.data.mapper.book

import raf.console.chitalka.data.local.dto.BookEntity
import raf.console.chitalka.domain.library.book.Book

interface BookMapper {
    suspend fun toBookEntity(book: Book): BookEntity

    suspend fun toBook(bookEntity: BookEntity): Book
}