package raf.console.chitalka.data.mapper.note

import raf.console.chitalka.data.local.dto.NoteEntity
import raf.console.chitalka.domain.reader.Note
import javax.inject.Inject

class NoteMapperImpl @Inject constructor() : NoteMapper {
    override suspend fun toEntity(note: Note): NoteEntity {
        return NoteEntity(
            id = note.id,
            bookId = note.bookId,
            chapterIndex = note.chapterIndex,
            offsetStart = note.offsetStart,
            offsetEnd = note.offsetEnd,
            content = note.content,
            createdAt = note.createdAt,
            bookmarkId = note.bookmarkId
        )
    }

    override suspend fun toDomain(entity: NoteEntity): Note {
        return Note(
            id = entity.id,
            bookId = entity.bookId,
            chapterIndex = entity.chapterIndex,
            offsetStart = entity.offsetStart,
            offsetEnd = entity.offsetEnd,
            content = entity.content,
            createdAt = entity.createdAt,
            bookmarkId = entity.bookmarkId
        )
    }
}
