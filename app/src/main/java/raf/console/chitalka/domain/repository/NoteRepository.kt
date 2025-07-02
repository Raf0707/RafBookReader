package raf.console.chitalka.domain.repository

import kotlinx.coroutines.flow.Flow
import raf.console.chitalka.domain.reader.Note

interface NoteRepository {
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun upsertNote(note: Note)

    suspend fun getNoteById(id: Long): Note?
    suspend fun getNotesForBook(bookId: Long): List<Note>
    suspend fun getNotesForBookmark(bookmarkId: Long): List<Note>
    suspend fun getAllNotes(): List<Note>
    suspend fun countNotes(): Int
    fun observeAllNotes(): Flow<List<Note>>
    fun observeNotesForBook(bookId: Long): Flow<List<Note>>
    fun observeNotesForBookmark(bookmarkId: Long): Flow<List<Note>>
}
