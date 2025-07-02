package raf.console.chitalka.domain.use_case.note

import kotlinx.coroutines.flow.Flow
import raf.console.chitalka.domain.reader.Note
import raf.console.chitalka.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNote @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) = repository.deleteNote(note)
}