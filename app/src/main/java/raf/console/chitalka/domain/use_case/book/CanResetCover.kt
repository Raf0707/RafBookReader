package raf.console.chitalka.domain.use_case.book

import raf.console.chitalka.domain.repository.BookRepository
import javax.inject.Inject

class CanResetCover @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(bookId: Int): Boolean {
        return repository.canResetCover(bookId)
    }
}