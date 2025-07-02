/*
 * RafBook — a modified fork of Book's Story, a free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * Modified by Raf</>Console Studio for RafBook
 * SPDX-License-Identifier: GPL-3.0-only
 */

package raf.console.chitalka.domain.repository

import raf.console.chitalka.domain.library.book.Book
import raf.console.chitalka.domain.library.book.BookWithCover
import raf.console.chitalka.domain.reader.ReaderText
import raf.console.chitalka.domain.util.CoverImage

interface BookRepository {

    suspend fun getBooks(
        query: String
    ): List<Book>

    suspend fun getBooksById(
        ids: List<Int>
    ): List<Book>

    suspend fun getBookText(
        bookId: Int
    ): List<ReaderText>

    suspend fun insertBook(
        bookWithCover: BookWithCover
    )

    suspend fun updateBook(
        book: Book
    )

    suspend fun updateCoverImageOfBook(
        bookWithOldCover: Book,
        newCoverImage: CoverImage?
    )

    suspend fun deleteBooks(
        books: List<Book>
    )

    suspend fun canResetCover(
        bookId: Int
    ): Boolean

    suspend fun resetCoverImage(
        bookId: Int
    ): Boolean

    suspend fun setCategories(
        bookId: Int,
        categoryIds: List<Int>
    )

    suspend fun addBookToCategory(
        bookId: Int,
        categoryId: Int
    )

    suspend fun removeBookFromCategory(
        bookId: Int,
        categoryId: Int
    )
}