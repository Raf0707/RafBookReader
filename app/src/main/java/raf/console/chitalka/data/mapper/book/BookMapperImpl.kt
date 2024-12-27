package raf.console.chitalka.data.mapper.book

import android.net.Uri
import raf.console.chitalka.R
import raf.console.chitalka.data.local.dto.BookEntity
import raf.console.chitalka.domain.model.Book
import raf.console.chitalka.domain.util.UIText
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
            textPath = book.textPath,
            description = book.description,
            image = book.coverImage?.toString(),
            category = book.category,
            chapters = book.chapters
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
            textPath = bookEntity.textPath,
            filePath = bookEntity.filePath,
            lastOpened = null,
            category = bookEntity.category,
            coverImage = if (bookEntity.image != null) Uri.parse(bookEntity.image) else null,
            chapters = bookEntity.chapters
        )
    }
}