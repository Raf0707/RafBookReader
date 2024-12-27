package raf.console.chitalka.domain.use_case.book

import raf.console.chitalka.domain.model.Book
import raf.console.chitalka.domain.repository.BookRepository
import javax.inject.Inject

class UpdateBook @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(book: Book) {
        repository.updateBook(book)
    }
}