package raf.console.chitalka.data.mapper.book

import raf.console.chitalka.data.local.dto.BookEntity
import raf.console.chitalka.domain.model.Book

interface BookMapper {
    suspend fun toBookEntity(book: Book): BookEntity

    suspend fun toBook(bookEntity: BookEntity): Book
}