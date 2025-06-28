/*
 * EverBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by ByteFlipper for EverBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.use_case.book

import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.domain.repository.BookRepository
import javax.inject.Inject

class UpdateBook @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(book: Book) {
        repository.updateBook(book)
    }
}