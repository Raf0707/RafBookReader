package raf.console.chitalka.data.mapper.bookmark

import raf.console.chitalka.data.local.dto.BookmarkEntity
import raf.console.chitalka.domain.reader.Bookmark
import javax.inject.Inject

class BookmarkMapperImpl @Inject constructor() : BookmarkMapper {
    override suspend fun toEntity(bookmark: Bookmark): BookmarkEntity {
        return BookmarkEntity(
            id = bookmark.id,
            bookId = bookmark.bookId,
            chapterIndex = bookmark.chapterIndex,
            offset = bookmark.offset,
            label = bookmark.label,
            createdAt = bookmark.createdAt,
            progress = bookmark.progress
        )
    }

    override suspend fun toDomain(entity: BookmarkEntity): Bookmark {
        return Bookmark(
            id = entity.id,
            bookId = entity.bookId,
            chapterIndex = entity.chapterIndex,
            offset = entity.offset,
            label = entity.label,
            createdAt = entity.createdAt,
            progress = entity.progress
        )
    }
}
