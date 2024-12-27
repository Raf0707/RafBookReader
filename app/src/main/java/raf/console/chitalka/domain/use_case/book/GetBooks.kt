package raf.console.chitalka.domain.use_case.book

import raf.console.chitalka.domain.model.Book
import raf.console.chitalka.domain.repository.BookRepository
import javax.inject.Inject

class GetBooks @Inject constructor(
    private val repository: BookRepository
) {

    suspend fun execute(query: String): List<Book> {
        return repository.getBooks(query)
    }
}