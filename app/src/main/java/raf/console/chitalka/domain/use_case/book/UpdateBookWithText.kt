package raf.console.chitalka.domain.use_case.book

import raf.console.chitalka.domain.model.BookWithText
import raf.console.chitalka.domain.repository.BookRepository
import javax.inject.Inject

class UpdateBookWithText @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(bookWithText: BookWithText): Boolean {
        return repository.updateBookWithText(bookWithText)
    }
}