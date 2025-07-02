package raf.console.chitalka.data.mapper.note

import raf.console.chitalka.data.local.dto.NoteEntity
import raf.console.chitalka.domain.reader.Note

interface NoteMapper {
    suspend fun toEntity(note: Note): NoteEntity
    suspend fun toDomain(entity: NoteEntity): Note
}
