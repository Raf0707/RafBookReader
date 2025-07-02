package raf.console.chitalka.data.mapper.bookmark

import raf.console.chitalka.data.local.dto.BookmarkEntity
import raf.console.chitalka.domain.reader.Bookmark

interface BookmarkMapper {
    suspend fun toEntity(bookmark: Bookmark): BookmarkEntity
    suspend fun toDomain(entity: BookmarkEntity): Bookmark
}
