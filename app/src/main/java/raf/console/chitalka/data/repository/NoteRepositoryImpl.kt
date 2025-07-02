package raf.console.chitalka.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import raf.console.chitalka.data.local.room.NoteDao
import raf.console.chitalka.data.mapper.note.NoteMapper
import raf.console.chitalka.domain.reader.Note
import raf.console.chitalka.domain.repository.NoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val mapper: NoteMapper
) : NoteRepository {

    override suspend fun insertNote(note: Note): Long {
        return noteDao.insert(mapper.toEntity(note))
    }

    override suspend fun updateNote(note: Note) {
        noteDao.update(mapper.toEntity(note))
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.delete(mapper.toEntity(note))
    }

    override suspend fun upsertNote(note: Note) {
        noteDao.upsert(mapper.toEntity(note))
    }

    override suspend fun getNoteById(id: Long): Note? {
        return noteDao.getById(id.toInt())?.let { mapper.toDomain(it) }
    }

    override suspend fun getNotesForBook(bookId: Long): List<Note> {
        return noteDao.getForBook(bookId.toInt()).map { mapper.toDomain(it) }
    }

    override suspend fun getNotesForBookmark(bookmarkId: Long): List<Note> {
        return noteDao.getForBookmark(bookmarkId.toInt()).map { mapper.toDomain(it) }
    }

    override suspend fun getAllNotes(): List<Note> {
        return noteDao.getAll().map { mapper.toDomain(it) }
    }

    override suspend fun countNotes(): Int {
        return noteDao.count()
    }

    override fun observeAllNotes(): Flow<List<Note>> {
        return noteDao.observeAll().map { list -> list.map { mapper.toDomain(it) } }
    }

    override fun observeNotesForBook(bookId: Long): Flow<List<Note>> {
        return noteDao.observeForBookmark(bookId.toInt()).map { list -> list.map { mapper.toDomain(it) } }
    }

    override fun observeNotesForBookmark(bookmarkId: Long): Flow<List<Note>> {
        return noteDao.observeForBookmark(bookmarkId.toInt()).map { list -> list.map { mapper.toDomain(it) } }
    }
}
