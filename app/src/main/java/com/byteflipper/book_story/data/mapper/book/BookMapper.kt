package com.byteflipper.book_story.data.mapper.book

import com.byteflipper.book_story.data.local.dto.BookEntity
import com.byteflipper.book_story.domain.model.Book

interface BookMapper {
    suspend fun toBookEntity(book: Book): BookEntity

    suspend fun toBook(bookEntity: BookEntity): Book
}