package raf.console.chitalka.domain.use_case.book

import raf.console.chitalka.domain.model.BookWithTextAndCover
import raf.console.chitalka.domain.repository.BookRepository
import javax.inject.Inject

class InsertBook @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(
        bookWithTextAndCover: BookWithTextAndCover
    ): Boolean {
        return repository.insertBook(bookWithTextAndCover)
    }
}